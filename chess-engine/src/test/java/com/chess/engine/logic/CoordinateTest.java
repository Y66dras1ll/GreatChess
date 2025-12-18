package com.chess.engine.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    @DisplayName("Проверка конструктора с char и int")
    void testCharIntConstructor() {
        Coordinate coord = new Coordinate('e', 4);
        assertEquals('e', coord.getFile());
        assertEquals(4, coord.getRank());
        assertFalse(coord.isEmpty());
    }

    @Test
    @DisplayName("Проверка конструктора копирования")
    void testCopyConstructor() {
        Coordinate original = new Coordinate('d', 5);
        Coordinate copy = new Coordinate(original);
        assertEquals(original.getFile(), copy.getFile());
        assertEquals(original.getRank(), copy.getRank());
        assertNotSame(original, copy);
    }

    @Test
    @DisplayName("Проверка строкового конструктора с корректной строкой")
    void testStringConstructorValid() {
        Coordinate coord = new Coordinate("h8");
        assertEquals('h', coord.getFile());
        assertEquals(8, coord.getRank());
        assertFalse(coord.isEmpty());
    }

    @Test
    @DisplayName("Проверка строкового конструктора с двухзначным числом")
    void testStringConstructorTwoDigitNumber() {
        Coordinate coord = new Coordinate("j10");
        assertEquals('j', coord.getFile());
        assertEquals(10, coord.getRank());
        assertFalse(coord.isEmpty());
        assertTrue(coord.isInBoard());
    }

    @Test
    @DisplayName("Проверка строкового конструктора с некорректной строкой")
    void testStringConstructorInvalid() {
        Coordinate coord = new Coordinate("invalid");
        assertEquals(0, coord.getFile());
        assertEquals(0, coord.getRank());
        assertTrue(coord.isEmpty());
    }

    @Test
    @DisplayName("Проверка строкового конструктора с пустой строкой")
    void testStringConstructorEmptyString() {
        Coordinate coord = new Coordinate("");
        assertEquals(0, coord.getFile());
        assertEquals(0, coord.getRank());
        assertTrue(coord.isEmpty());
    }

    @Test
    @DisplayName("Проверка строкового конструктора с пробелами")
    void testStringConstructorWithSpaces() {
        Coordinate coord = new Coordinate("  e4  ");
        assertEquals('e', coord.getFile());
        assertEquals(4, coord.getRank());
        assertFalse(coord.isEmpty());
    }

    @Test
    @DisplayName("Проверка строкового конструктора с координатой вне доски")
    void testStringConstructorOutOfBounds() {
        Coordinate coord = new Coordinate("k5");
        assertEquals(0, coord.getFile());
        assertEquals(0, coord.getRank());
        assertTrue(coord.isEmpty());
    }

    @Test
    @DisplayName("Проверка пустого конструктора")
    void testEmptyConstructor() {
        Coordinate coord = new Coordinate();
        assertEquals(0, coord.getFile());
        assertEquals(0, coord.getRank());
        assertTrue(coord.isEmpty());
        assertFalse(coord.isInBoard());
    }

    @Test
    @DisplayName("Проверка метода getFile")
    void testGetFile() {
        Coordinate coord = new Coordinate('a', 1);
        assertEquals('a', coord.getFile());
    }

    @Test
    @DisplayName("Проверка метода getRank")
    void testGetRank() {
        Coordinate coord = new Coordinate('a', 1);
        assertEquals(1, coord.getRank());
    }

    @Test
    @DisplayName("Проверка метода getFileIndex")
    void testGetFileIndex() {
        Coordinate coordA = new Coordinate('a', 1);
        assertEquals(0, coordA.getFileIndex());

        Coordinate coordJ = new Coordinate('j', 1);
        assertEquals(9, coordJ.getFileIndex());

        Coordinate invalid = new Coordinate();
        assertEquals(-1, invalid.getFileIndex());
    }

    @Test
    @DisplayName("Проверка метода getRankIndex")
    void testGetRankIndex() {
        Coordinate coord1 = new Coordinate('a', 1);
        assertEquals(0, coord1.getRankIndex());

        Coordinate coord10 = new Coordinate('a', 10);
        assertEquals(9, coord10.getRankIndex());

        Coordinate invalid = new Coordinate();
        assertEquals(-1, invalid.getRankIndex());
    }

    @Test
    @DisplayName("Проверка метода isEmpty")
    void testIsEmpty() {
        assertTrue(new Coordinate().isEmpty());
        assertTrue(new Coordinate((char)0, 0).isEmpty());
        assertTrue(new Coordinate("invalid").isEmpty());
        assertFalse(new Coordinate('a', 1).isEmpty());
        assertFalse(new Coordinate("j10").isEmpty());
    }

    @Test
    @DisplayName("Проверка метода isInBoard")
    void testIsInBoard() {
        assertTrue(new Coordinate('a', 1).isInBoard());
        assertTrue(new Coordinate('j', 10).isInBoard());
        assertTrue(new Coordinate('e', 5).isInBoard());
        assertFalse(new Coordinate().isInBoard());
        assertFalse(new Coordinate("k5").isInBoard());
        assertFalse(new Coordinate('a', 11).isInBoard());
    }

    @Test
    @DisplayName("Проверка статического метода inBoard с валидными координатами")
    void testStaticInBoardValid() {
        assertTrue(Coordinate.inBoard(new Coordinate('a', 1)));
        assertTrue(Coordinate.inBoard(new Coordinate('j', 10)));
        assertTrue(Coordinate.inBoard(new Coordinate('e', 5)));
    }

    @Test
    @DisplayName("Проверка статического метода inBoard с невалидными координатами")
    void testStaticInBoardInvalid() {
        assertFalse(Coordinate.inBoard(new Coordinate('a', 0)));
        assertFalse(Coordinate.inBoard(new Coordinate('k', 5)));
        assertFalse(Coordinate.inBoard(new Coordinate('a', 11)));
        assertFalse(Coordinate.inBoard(new Coordinate('@', 5)));
        assertFalse(Coordinate.inBoard(new Coordinate()));
        assertFalse(Coordinate.inBoard(null));
    }

    @Test
    @DisplayName("Проверка граничных значений для inBoard")
    void testInBoardBoundaries() {
        assertTrue(Coordinate.inBoard(new Coordinate('a', 1)));
        assertTrue(Coordinate.inBoard(new Coordinate('j', 10)));
        assertFalse(Coordinate.inBoard(new Coordinate('a', 0)));
        assertFalse(Coordinate.inBoard(new Coordinate('j', 11)));
        assertFalse(Coordinate.inBoard(new Coordinate('`', 1)));
        assertFalse(Coordinate.inBoard(new Coordinate('k', 1)));
    }

    @Test
    @DisplayName("Проверка метода toString для валидных координат")
    void testToStringValid() {
        assertEquals("a1", new Coordinate('a', 1).toString());
        assertEquals("j10", new Coordinate('j', 10).toString());
        assertEquals("e4", new Coordinate('e', 4).toString());
    }

    @Test
    @DisplayName("Проверка метода toString для невалидных координат")
    void testToStringInvalid() {
        assertEquals("INVALID", new Coordinate().toString());
        assertEquals("INVALID", new Coordinate("invalid").toString());
        assertEquals("INVALID", new Coordinate((char)0, 0).toString());
    }

    @Test
    @DisplayName("Проверка равенства координат")
    void testEquals() {
        Coordinate coord1 = new Coordinate('e', 4);
        Coordinate coord2 = new Coordinate('e', 4);
        Coordinate coord3 = new Coordinate('d', 4);
        Coordinate coord4 = new Coordinate('e', 5);
        Coordinate coord5 = new Coordinate("e4");
        Coordinate coord6 = new Coordinate("E4");

        assertEquals(coord1, coord2);
        assertEquals(coord1, coord5);
        assertEquals(coord1, coord6);
        assertNotEquals(coord1, coord3);
        assertNotEquals(coord1, coord4);
        assertEquals(coord1, coord1);
        assertNotEquals(coord1, null);
        assertNotEquals(coord1, "e4");
    }

    @Test
    @DisplayName("Проверка хэш-кода")
    void testHashCode() {
        Coordinate coord1 = new Coordinate('e', 4);
        Coordinate coord2 = new Coordinate('e', 4);
        Coordinate coord3 = new Coordinate('d', 4);
        Coordinate coord4 = new Coordinate("e4");

        assertEquals(coord1.hashCode(), coord2.hashCode());
        assertEquals(coord1.hashCode(), coord4.hashCode());
        assertNotEquals(coord1.hashCode(), coord3.hashCode());
    }

    @Test
    @DisplayName("Проверка пустой координаты")
    void testEmptyCoordinate() {
        assertEquals(0, Coordinate.emptyCoordinate.getFile());
        assertEquals(0, Coordinate.emptyCoordinate.getRank());
        assertTrue(Coordinate.emptyCoordinate.isEmpty());
        assertFalse(Coordinate.inBoard(Coordinate.emptyCoordinate));
    }

    @Test
    @DisplayName("Проверка автоматического приведения к нижнему регистру")
    void testLowerCaseConversion() {
        Coordinate upperCase = new Coordinate('E', 4);
        assertEquals('e', upperCase.getFile());
        assertEquals(4, upperCase.getRank());
    }

    @Test
    @DisplayName("Проверка всех возможных координат доски")
    void testAllBoardCoordinates() {
        for (char file = 'a'; file <= 'j'; file++) {
            for (int rank = 1; rank <= 10; rank++) {
                Coordinate coord = new Coordinate(file, rank);
                assertTrue(coord.isInBoard(), "Координата " + file + rank + " должна быть на доске");
                assertEquals(file, coord.getFile());
                assertEquals(rank, coord.getRank());
            }
        }
    }

    @Test
    @DisplayName("Проверка различных форматов строкового конструктора")
    void testVariousStringFormats() {
        assertEquals('a', new Coordinate("a1").getFile());
        assertEquals(1, new Coordinate("a1").getRank());

        assertEquals('e', new Coordinate("E5").getFile());
        assertEquals(5, new Coordinate("E5").getRank());

        assertEquals('j', new Coordinate("j10").getFile());
        assertEquals(10, new Coordinate("j10").getRank());
    }

    @Test
    @DisplayName("Проверка координаты j10 через конструктор char-int")
    void testJ10Coordinate() {
        Coordinate j10 = new Coordinate('j', 10);
        assertEquals('j', j10.getFile());
        assertEquals(10, j10.getRank());
        assertTrue(j10.isInBoard());
        assertEquals("j10", j10.toString());
        assertEquals(9, j10.getFileIndex());
        assertEquals(9, j10.getRankIndex());
    }

    @Test
    @DisplayName("Проверка координат с двухзначными числами вне диапазона")
    void testTwoDigitOutOfRange() {
        Coordinate coord1 = new Coordinate("a11");
        assertTrue(coord1.isEmpty());
        assertEquals("INVALID", coord1.toString());

        Coordinate coord2 = new Coordinate("a0");
        assertTrue(coord2.isEmpty());
        assertEquals("INVALID", coord2.toString());
    }

    @Test
    @DisplayName("Проверка некорректных форматов строк")
    void testInvalidStringFormats() {
        assertTrue(new Coordinate("a").isEmpty());

        assertTrue(new Coordinate("10").isEmpty());

        assertTrue(new Coordinate("1a").isEmpty());

        assertTrue(new Coordinate("a100").isEmpty());

        assertTrue(new Coordinate("@1").isEmpty());
    }

    @Test
    @DisplayName("Проверка копирования невалидной координаты")
    void testCopyInvalidCoordinate() {
        Coordinate invalid = new Coordinate("invalid");
        Coordinate copy = new Coordinate(invalid);
        assertEquals(0, copy.getFile());
        assertEquals(0, copy.getRank());
        assertTrue(copy.isEmpty());
    }
}