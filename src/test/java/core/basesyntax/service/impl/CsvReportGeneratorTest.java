package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.InventoryDao;
import core.basesyntax.dao.InventoryDaoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvReportGeneratorTest {
    private static final String VALID_RESUL_STRING = "fruit,quantity"
                                                    + System.lineSeparator()
                                                    + "banana,152";
    private static InventoryDao inventoryDao;
    private static CsvReportGenerator csvReportGenerator;

    @BeforeAll
    public static void setUp() {
        csvReportGenerator = new CsvReportGenerator();
        inventoryDao = new InventoryDaoImpl();
    }

    @AfterEach
    public void cleanInventory() {
        inventoryDao.getCurrentInventoryState().clear();
    }

    @Test
    public void generateReport_allValidConditions() {
        inventoryDao.putToInventory("banana", 152);
        String actual = csvReportGenerator.generateReport();
        assertEquals(actual, VALID_RESUL_STRING);
    }

    @Test
    public void generateReport_fromEmptyInventory_NotOk() {
        assertThrows(RuntimeException.class, () -> csvReportGenerator.generateReport());
    }

    @Test
    public void generateReport_with_RandomDataInInventory_Ok() {
        for (int i = 0; i < 3; i++) {
            inventoryDao.putToInventory("fruit" + i, i);
        }
        String actual = csvReportGenerator.generateReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                            + "fruit2," + 2 + System.lineSeparator()
                            + "fruit1," + 1 + System.lineSeparator()
                            + "fruit0," + 0;
        assertEquals(actual, expected);
    }
}