package dungeonmania.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.Destroyable;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Moveable;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.Portal;
import dungeonmania.entities.Switch;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.ZombieToastSpawner;
import dungeonmania.entities.logical.LogicalEntity;
import dungeonmania.entities.logical.Toggleable;
import dungeonmania.entities.logical.Wire;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class GameMap {
    private Game game;
    private Map<Position, GraphNode> nodes = new HashMap<>();

    /**
     * Initialise the game map
     * 1. pair up portals
     * 2. register all movables
     * 3. register all spawners
     * 4. register bombs and switches
     * 5. more...
     */
    public void init() {
        initPairPortals();
        initRegisterMovables();
        initRegisterSpawners();
        initRegisterBombsAndSwitches();
    }

    private void initRegisterBombsAndSwitches() {
        List<Bomb> bombs = getEntities(Bomb.class);
        List<Switch> switchs = getEntities(Switch.class);
        for (Bomb b : bombs) {
            for (Switch s : switchs) {
                if (Position.isAdjacent(b.getPosition(), s.getPosition())) {
                    b.subscribe(s);
                    s.subscribe(b);
                }
            }
        }
    }

    // Pair up portals if there's any
    private void initPairPortals() {
        Map<String, Portal> portalsMap = new HashMap<>();
        nodes.forEach((k, v) -> {
            v.getEntities().stream().filter(Portal.class::isInstance).map(Portal.class::cast).forEach(portal -> {
                String color = portal.getColor();
                if (portalsMap.containsKey(color)) {
                    portal.bind(portalsMap.get(color));
                } else {
                    portalsMap.put(color, portal);
                }
            });
        });
    }

    private void initRegisterMovables() {
        List<Enemy> enemies = getEntities(Enemy.class);
        enemies.forEach(e -> {
            game.registerEnemyMovement(e);
        });
    }

    private void initRegisterSpawners() {
        List<ZombieToastSpawner> zts = getEntities(ZombieToastSpawner.class);
        zts.forEach(e -> {
            game.register(() -> e.spawn(game), Game.AI_MOVEMENT, e.getId());
        });
        game.register(() -> game.getEntityFactory().spawnSpider(game), Game.AI_MOVEMENT, "spawnSpiders");
    }

    public void moveTo(Entity entity, Position position) {
        if (!canMoveTo(entity, position))
            return;

        triggerMovingAwayEvent(entity);
        removeNode(entity);
        entity.setPosition(position);
        addEntity(entity);
        triggerOverlapEvent(entity);
    }

    public void moveTo(Entity entity, Direction direction) {
        if (!canMoveTo(entity, Position.translateBy(entity.getPosition(), direction)))
            return;
        triggerMovingAwayEvent(entity);
        removeNode(entity);
        entity.setPosition(Position.translateBy(entity.getPosition(), direction));
        addEntity(entity);
        triggerOverlapEvent(entity);
    }

    private void triggerMovingAwayEvent(Entity entity) {
        List<Runnable> callbacks = new ArrayList<>();
        getEntities(entity.getPosition()).forEach(e -> {
            if (e != entity && e instanceof Moveable)
                callbacks.add(() -> ((Moveable) e).onMovedAway(game, entity));
        });
        callbacks.forEach(callback -> {
            callback.run();
        });
    }

    private void triggerOverlapEvent(Entity entity) {
        List<Runnable> overlapCallbacks = new ArrayList<>();
        getEntities(entity.getPosition()).forEach(e -> {
            if (e != entity && e instanceof Overlappable)
                overlapCallbacks.add(() -> ((Overlappable) e).onOverlap(game, entity));
        });
        overlapCallbacks.forEach(callback -> {
            callback.run();
        });
    }

    public boolean canMoveTo(Entity entity, Position position) {
        return !nodes.containsKey(position) || nodes.get(position).canMoveOnto(this, entity);
    }

    public Position dijkstraPathFind(Position src, Position dest, Entity entity) {
        // if inputs are invalid, don't move
        if (!nodes.containsKey(src) || !nodes.containsKey(dest))
            return src;

        Map<Position, Integer> dist = new HashMap<>();
        Map<Position, Position> prev = new HashMap<>();
        Map<Position, Boolean> visited = new HashMap<>();

        prev.put(src, null);
        dist.put(src, 0);

        PriorityQueue<Position> q = new PriorityQueue<>((x, y) -> Integer
                .compare(dist.getOrDefault(x, Integer.MAX_VALUE), dist.getOrDefault(y, Integer.MAX_VALUE)));
        q.add(src);

        while (!q.isEmpty()) {
            Position curr = q.poll();
            if (curr.equals(dest) || dist.get(curr) > 200)
                break;
            // check portal
            if (nodes.containsKey(curr) && nodes.get(curr).getEntities().stream().anyMatch(Portal.class::isInstance)) {
                Portal portal = nodes.get(curr).getEntities().stream().filter(Portal.class::isInstance)
                        .map(Portal.class::cast).collect(Collectors.toList()).get(0);
                List<Position> teleportDest = portal.getDestPositions(this, entity);
                teleportDest.stream().filter(p -> !visited.containsKey(p)).forEach(p -> {
                    dist.put(p, dist.get(curr));
                    prev.put(p, prev.get(curr));
                    q.add(p);
                });
                continue;
            }
            visited.put(curr, true);
            List<Position> neighbours = curr.getCardinallyAdjacentPositions().stream()
                    .filter(p -> !visited.containsKey(p))
                    .filter(p -> !nodes.containsKey(p) || nodes.get(p).canMoveOnto(this, entity))
                    .collect(Collectors.toList());

            neighbours.forEach(n -> {
                int newDist = dist.get(curr) + (nodes.containsKey(n) ? nodes.get(n).getWeight() : 1);
                if (newDist < dist.getOrDefault(n, Integer.MAX_VALUE)) {
                    q.remove(n);
                    dist.put(n, newDist);
                    prev.put(n, curr);
                    q.add(n);
                }
            });
        }
        Position ret = dest;
        if (prev.get(ret) == null || ret.equals(src))
            return src;
        while (!prev.get(ret).equals(src)) {
            ret = prev.get(ret);
        }
        return ret;
    }

    public void removeNode(Entity entity) {
        Position p = entity.getPosition();
        if (nodes.containsKey(p)) {
            nodes.get(p).removeEntity(entity);
            if (nodes.get(p).size() == 0) {
                nodes.remove(p);
            }
        }
    }

    public void destroyEntity(Entity entity) {
        removeNode(entity);

        if (entity instanceof Destroyable) {
            ((Destroyable) entity).onDestroy(game);
        }
    }

    public void addEntity(Entity entity) {
        addNode(new GraphNode(entity));
    }

    public void addNode(GraphNode node) {
        Position p = node.getPosition();

        if (!nodes.containsKey(p))
            nodes.put(p, node);
        else {
            GraphNode curr = nodes.get(p);
            curr.mergeNode(node);
            nodes.put(p, curr);
        }
    }

    /**
     *
     * @param currPos: Position where we want to check the 4 cardinally adjacent spots
     * @param sameTick: Boolean - true if we want to find the max number of active currents activated on the same tick
     * @return
     */
    public Integer getNumAdjacentActiveCurrents(Position currPos, boolean sameTick) {
        List<Position> adjacentActiveCurrents = new ArrayList<Position>();
        List<Integer> activatedTickNumbers = new ArrayList<Integer>();

        List<Position> neighbours = currPos.getCardinallyAdjacentPositions();

        for (Position p : neighbours) {
            if (!nodes.containsKey(p)) {
                continue;
            }

            // FIXME demeter rip
            GraphNode currNode = nodes.get(p);
            List<Entity> entities = currNode.getEntities();

            for (Entity e : entities) {
                // FIXME could do currNode.getLogicalEntities/getCurrentEntities to make this a bit nicer
                if (e instanceof Wire) {
                    if (((Wire) e).hasCurrent()) {
                        adjacentActiveCurrents.add(p);
                        activatedTickNumbers.add(((Wire) e).getActivatedTickNumber());
                    }
                }
            }

        }

        // this is probs overkill but idk how else to find the number of same ticks haha
        if (sameTick) {
            Map<Integer, Integer> frequencyMap = new HashMap<>();

            // Hashmap with key = activated tick number, value = number of times the activated tick number appears
            for (int num : activatedTickNumbers) {
                frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
            }

            // find the activated tick number with the highest frequency
            int maxFreq = 0;
            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                int currFreq = entry.getValue();
                if (currFreq > maxFreq) {
                    maxFreq = currFreq;
                }
            }

            return maxFreq;
        }

        return adjacentActiveCurrents.size();
    }

    // FIXME repeated code rip
    public Integer getNumAdjacentCurrents(Position currPos) {
        List<Position> neighbours = currPos.getCardinallyAdjacentPositions();
        List<Position> adjacentCurrents = new ArrayList<Position>();

        for (Position p : neighbours) {
            if (!nodes.containsKey(p)) {
                continue;
            }

            GraphNode currNode = nodes.get(p);
            List<Entity> entities = currNode.getEntities();

            for (Entity e : entities) {
                if (e instanceof Wire) {
                    adjacentCurrents.add(p);
                }
            }
        }

        return adjacentCurrents.size();
    }

    /**
     *
     * @param startingPos: starting position to perform the BFS from
     * @param turnOn: boolean if we want to then the current entities on or off.
     * i.e. if turnOn = true, we want to turn the current entities on
     * @param tickCount: the number of the tick we are on
     * @param getReachable: boolean, true if we only want to get a list of reachable entities,
     * and not modify any entiites
     *
     */
    public List<LogicalEntity> bfsLogicalEntities(Position startingPos, boolean turnOn, int tickCount,
            boolean getReachable) {
        Map<Position, Boolean> visited = new HashMap<>();
        Queue<Position> queue = new LinkedList<>();
        List<LogicalEntity> reachableEntities = new ArrayList<>();

        visited.put(startingPos, true);
        queue.add(startingPos);

        while (queue.size() != 0) {
            Position currPos = queue.poll();

            GraphNode currNode = nodes.get(currPos);

            // TODO - added this if to stop all the regression tests from failing
            // don't know if this will work with new logic tests
            // need to figure out what nodes is
            if (currNode == null) {
                continue;
            }
            List<Entity> currEntities = currNode.getEntities();

            for (Entity e : currEntities) {
                if (e instanceof LogicalEntity) {
                    reachableEntities.add((LogicalEntity) e);
                }

                if (!(e instanceof Wire) || getReachable) {
                    continue;
                }

                Wire wire = (Wire) e;

                if (turnOn != wire.hasCurrent()) {
                    wire.toggle();
                    wire.setActivatedTickNumber(tickCount);
                }
            }

            List<Position> adjPositions = currPos.getCardinallyAdjacentPositions();
            for (Position p : adjPositions) {
                if (visited.containsKey(p)) {
                    continue;
                }
                visited.put(p, true);
                queue.add(p);
            }

        }

        return reachableEntities;
    }

    // For all on logical entities that are no longer reachable by wire (i.e. after bomb explodes),
    // turn them off
    // FIXME sorry about this typecasting rip
    public void toggleUnreachableEntities() {
        // Get all logical entities on the map
        List<Entity> allLogicalEntities = getEntities().stream().filter(e -> e instanceof Toggleable)
                .map(e -> (Entity) e).collect(Collectors.toList());
        List<Entity> reachableEntities = new ArrayList<>();

        // Do a BFS from all switches to get all reachable entities on the map (after the bomb has exploded)
        List<Position> allSwitchPositions = getEntities(Switch.class).stream().map(e -> e.getPosition())
                .collect(Collectors.toList());
        for (Position p : allSwitchPositions) {
            List<LogicalEntity> currReachable = bfsLogicalEntities(p, false, getTick(), true);
            for (LogicalEntity logicalEntity : currReachable) {
                reachableEntities.add((Entity) logicalEntity);
            }
        }

        // remove duplicates from list of reachable entities
        HashSet<Entity> uniqueReachableEntities = new HashSet<Entity>(reachableEntities);
        reachableEntities.clear();
        reachableEntities.addAll(uniqueReachableEntities);

        // Create a list of entities that aren't reachable anymore because the bomb exploded
        List<Entity> unreachableEntities = new ArrayList<>(allLogicalEntities);
        unreachableEntities.removeAll(reachableEntities);

        // Turn all these unreachable entities off
        for (Entity logicalEntity : unreachableEntities) {
            Toggleable toggleableLogicalEntity = (Toggleable) logicalEntity;
            toggleableLogicalEntity.turnOff();
        }
    }

    public void evaluateAllLogicalEntities() {
        // Get all logical entities on the map
        List<LogicalEntity> allLogicalEntities = getEntities().stream().filter(e -> e instanceof LogicalEntity)
                .map(e -> (LogicalEntity) e).collect(Collectors.toList());

        for (LogicalEntity logicalEntity : allLogicalEntities) {
            logicalEntity.evaluate(game);
        }
    }

    public Entity getEntity(String id) {
        Entity res = null;
        for (Map.Entry<Position, GraphNode> entry : nodes.entrySet()) {
            List<Entity> es = entry.getValue().getEntities().stream().filter(e -> e.getId().equals(id))
                    .collect(Collectors.toList());
            if (es != null && es.size() > 0) {
                res = es.get(0);
                break;
            }
        }
        return res;
    }

    public List<Entity> getEntities(Position p) {
        GraphNode node = nodes.get(p);
        return (node != null) ? node.getEntities() : new ArrayList<>();
    }

    public List<Entity> getEntities() {
        List<Entity> entities = new ArrayList<>();
        nodes.forEach((k, v) -> entities.addAll(v.getEntities()));
        return entities;
    }

    public <T extends Entity> List<T> getEntities(Class<T> type) {
        return getEntities().stream().filter(type::isInstance).map(type::cast).collect(Collectors.toList());
    }

    public <T extends Entity> boolean doesTypeExist(Class<T> type) {
        return !getEntities(type).isEmpty();
    }

    // unused
    // public Game getGame() {
    //     return game;
    // }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getTick() {
        return game.getTick();
    }
}
