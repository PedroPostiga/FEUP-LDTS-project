package com.gladiator.model.enemy;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.*;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.movement.MovementStrategy;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.WanderMovement;

import java.util.*;
import java.util.function.BiFunction;

public class EnemyPool {

    public enum EnemyType {
        VAMPIRE,
        FAT_ZOMBIE,
        LIGHT_ZOMBIE
    }

    private final Map<EnemyType, Queue<Enemy>> availablePools;
    private final Map<EnemyType, List<Enemy>> activeEnemies;
    private final Map<EnemyType, Integer> poolSizes;
    private final Gladiator gladiator;

    private static final int INITIAL_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 50;

    private final Map<EnemyType, BiFunction<Integer, Integer, Enemy>> enemyFactories;

    public EnemyPool(Gladiator gladiator) {
        this.gladiator = gladiator;
        this.availablePools = new EnumMap<>(EnemyType.class);
        this.activeEnemies = new EnumMap<>(EnemyType.class);
        this.poolSizes = new EnumMap<>(EnemyType.class);
        this.enemyFactories = new EnumMap<>(EnemyType.class);

        initializeFactories();
        initializePools();
    }

    private void initializeFactories() {
        enemyFactories.put(EnemyType.VAMPIRE, (x, y) -> new Vampire(
                x, y,
                new ChaseMovement(3.0, gladiator),
                new VampireAttack(15, 30, gladiator, 0.3)
        ));

        enemyFactories.put(EnemyType.FAT_ZOMBIE, (x, y) -> new FatZombie(
                x, y,
                new ChaseMovement(2.0, gladiator),
                new SwordAttack(12, 30, List.of(gladiator))
        ));

        enemyFactories.put(EnemyType.LIGHT_ZOMBIE, (x, y) -> {
            boolean shouldChase = (x + y) % 2 == 0;
            return new LightZombie(
                    x, y,
                    shouldChase ?
                            new ChaseMovement(4.0, gladiator) :
                            new WanderMovement(),
                    new SwordAttack(8, 30, List.of(gladiator))
            );
        });
    }

    private void initializePools() {
        for (EnemyType type : EnemyType.values()) {
            availablePools.put(type, new LinkedList<>());
            activeEnemies.put(type, new ArrayList<>());
            poolSizes.put(type, 0);
        }
    }

    public Enemy acquireEnemy(EnemyType type, int x, int y) {
        Queue<Enemy> pool = availablePools.get(type);
        Enemy enemy;

        if (!pool.isEmpty()) {
            enemy = pool.poll();
            enemy.setPosition(new Position(x, y));
            enemy.resetHealth();
            
        // For LightZombie, update movement strategy based on actual spawn position
        if (type == EnemyType.LIGHT_ZOMBIE && enemy instanceof LightZombie) {
            boolean shouldChase = (x + y) % 2 == 0;
            enemy.setMovement(shouldChase ?
                    new ChaseMovement(4.0, gladiator) :
                    new WanderMovement());
        }
        } else {
            if (poolSizes.get(type) < MAX_POOL_SIZE) {
                enemy = createNewEnemy(type, x, y);
                poolSizes.put(type, poolSizes.get(type) + 1);
            } else {
                return null;
            }
        }

        activeEnemies.get(type).add(enemy);
        return enemy;
    }

    public void releaseEnemy(Enemy enemy) {
        if (enemy == null) return;

        EnemyType type = determineEnemyType(enemy);
        if (type == null) return;

        activeEnemies.get(type).remove(enemy);
        enemy.reset();
        availablePools.get(type).offer(enemy);
    }

    public List<Enemy> getAllActiveEnemies() {
        List<Enemy> allActive = new ArrayList<>();
        for (List<Enemy> activeList : activeEnemies.values()) {
            allActive.addAll(activeList);
        }
        return allActive;
    }

    public void preWarmPools() {
        for (EnemyType type : EnemyType.values()) {
            Queue<Enemy> pool = availablePools.get(type);
            for (int i = 0; i < INITIAL_POOL_SIZE && poolSizes.get(type) < MAX_POOL_SIZE; i++) {
                Enemy enemy = createNewEnemy(type, -100, -100);
                pool.offer(enemy);
                poolSizes.put(type, poolSizes.get(type) + 1);
            }
        }
    }

    public void clear() {
        for (EnemyType type : EnemyType.values()) {
            availablePools.get(type).clear();
            activeEnemies.get(type).clear();
            poolSizes.put(type, 0);
        }
    }

    private Enemy createNewEnemy(EnemyType type, int x, int y) {
        return enemyFactories.get(type).apply(x, y);
    }

    private EnemyType determineEnemyType(Enemy enemy) {
        if (enemy instanceof Vampire) return EnemyType.VAMPIRE;
        if (enemy instanceof FatZombie) return EnemyType.FAT_ZOMBIE;
        if (enemy instanceof LightZombie) return EnemyType.LIGHT_ZOMBIE;
        return null;
    }
}