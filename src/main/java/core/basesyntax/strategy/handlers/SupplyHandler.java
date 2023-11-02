package core.basesyntax.strategy.handlers;

import core.basesyntax.dao.InventoryDao;
import core.basesyntax.strategy.OperationHandler;

public class SupplyHandler implements OperationHandler {
    private final InventoryDao inventoryDao;

    public SupplyHandler(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    @Override
    public void executeTransaction(String fruitName, int amount) {
        int newAmount = inventoryDao.getAmountByFruit(fruitName) + amount;
        inventoryDao.putToInventory(fruitName, newAmount);
    }
}