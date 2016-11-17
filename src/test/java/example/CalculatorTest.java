package example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(DataSetResolver.class)
public class CalculatorTest {

    Calculator cut = new Calculator();

    @TestFactory
    @RulesFile("addition-rules.txt")
    Stream<DynamicTest> addition(Stream<DataSet> dataSets) {
        return dataSets.map(dataSet -> dynamicTest(getAdditionDisplayName(dataSet), () -> {
            int result = cut.add(dataSet.getValueA(), dataSet.getValueB());
            assertThat(result).isEqualTo(dataSet.getExpectedResult());
        }));
    }

    String getAdditionDisplayName(DataSet dataSet) {
        return dataSet.getValueA() + " + " + dataSet.getValueB() + " = " + dataSet.getExpectedResult();
    }

    @TestFactory
    @RulesFile("subtraction-rules.txt")
    Stream<DynamicTest> subtraction(Stream<DataSet> dataSets) {
        return dataSets.map(dataSet -> dynamicTest(getSubtractionDisplayName(dataSet), () -> {
                int result = cut.subtract(dataSet.getValueA(), dataSet.getValueB());
                assertThat(result).isEqualTo(dataSet.getExpectedResult());
            }));
    }

    String getSubtractionDisplayName(DataSet dataSet) {
        return dataSet.getValueA() + " - " + dataSet.getValueB() + " = " + dataSet.getExpectedResult();
    }

    @Test
    void someOtherTest() {
        // Because IntelliJ is not detecting @TestFactory tests, yet.
    }

}
