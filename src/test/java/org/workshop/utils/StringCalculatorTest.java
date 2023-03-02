package org.workshop.utils;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class StringCalculatorTest {

  @Test
  void shouldReturnZeroInSumOfEmptyString() {
    int result = StringCalculator.sum("");
    Assertions.assertEquals(result, 0);
  }

  @ParameterizedTest
  @MethodSource("provideStringValuePairs")
  void shouldReturnSingleValueForSingleValueInString(String str, int expected) {
    int result = StringCalculator.sum(str);
    Assertions.assertEquals(expected, result);
  }

  private static Stream<Arguments> provideStringValuePairs() {
    return Stream.of(
        Arguments.of("0",0),
        Arguments.of("11",11),
        Arguments.of("1000",1000)
    );
  }

  @ParameterizedTest
  @MethodSource("provideTwoCommaSeparatedValuePairs")
  void shouldSumTwoCommaSeparatedInts(String str, int expected) {
    int result = StringCalculator.sum(str);
    Assertions.assertEquals(expected, result);
  }

  private static Stream<Arguments> provideTwoCommaSeparatedValuePairs() {
    return Stream.of(
        Arguments.of("1,1", 2),
        Arguments.of("1,11",12),
        Arguments.of("12,12","24")
    );
  }

  @ParameterizedTest
  @MethodSource("provideTwoNewLineSeparatedValuePairs")
  void shouldSumTwoNewLineSeparatedInts(String str, int expected) {
    int result = StringCalculator.sum(str);
    Assertions.assertEquals(expected, result);
  }

  private static Stream<Arguments> provideTwoNewLineSeparatedValuePairs() {
    return Stream.of(
        Arguments.of("1\n2",3),
        Arguments.of("1\n1",2),
        Arguments.of("1\n10",11)
    );
  }

  @ParameterizedTest
  @MethodSource("provideCommaAndNewLineSeparatedValuePairs")
  void shouldSumCommaAndNewLineSeparatedValues(String str, int expected) {
    int result = StringCalculator.sum(str);
    Assertions.assertEquals(expected, result);
  }
  private static Stream<Arguments> provideCommaAndNewLineSeparatedValuePairs() {
    return Stream.of(
        Arguments.of("1,1,10",12),
        Arguments.of("1\n1\n1,1",4),
        Arguments.of(",1,1",2)
    );
  }

  @ParameterizedTest
  @MethodSource("provideCommaAndNewLineSeparatedValuesWithGreaterThan1000Pairs")
  void shouldSumValuesNotGreaterThanThousand(String str, int expected) {
    int result = StringCalculator.sum(str);
    Assertions.assertEquals(expected, result);
  }

  private static Stream<Arguments> provideCommaAndNewLineSeparatedValuesWithGreaterThan1000Pairs() {
    return Stream.of(
        Arguments.of("1000,1,1",1002),
        Arguments.of("1\n1\n1001,1",3),
        Arguments.of(",10000,1001", 0)
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"-1","-1000,100","1\n2\n3,3\n-1"})
  void shouldThrowExceptionForNegativeNumbers(String str) {
    IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, ()->{
      StringCalculator.sum(str);
    });
    Assertions.assertEquals("negative value of one of arguments", exception.getMessage());
  }

  @ParameterizedTest
  @MethodSource("provideValuesWithCustomDelimiter")
  void shouldParseWithAdditionalDelimiter(String str, int expected) {
    int result = StringCalculator.sum(str);
    Assertions.assertEquals(expected, result);
  }

  private static Stream<Arguments> provideValuesWithCustomDelimiter() {
    return Stream.of(
        Arguments.of("//#1,1#2",4),
        Arguments.of("//;1;1;\n\n100",102),
        Arguments.of("//p1,1,1",3),
        Arguments.of("//m1,1m3",5)
    );
  }
}