package org.xproce.advancedconcepts;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.xproce.BMICalculator;
import org.xproce.Coder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {


    private String environement = "dev" ;


    // This method should be static,
    // it is used for operations that should be run exactly once
    // before all other tests are performed.
    // for example setting a database connections or starting a server

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit tests");
    }


    //used to stop database connection sor stop servers
    @AfterAll
    static void afterAll() {
        System.out.println("After all unit tests.");
    }

    /*
        @Test
        void test() {
            assertTrue(BMICalculator.isDietRecommended(89.0, 1.72));
        }
    */


    @ParameterizedTest
    @CsvSource( value= {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"})
    void should_ReturnTrue_When_Diet_Recommended_DoubleValues(Double coderWeight, Double coderHeight) {
        //given
        double weight = coderWeight;
        double height = coderHeight;
        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        //then
        assertTrue(recommended);
    }


    @ParameterizedTest
    @ValueSource(doubles = {75.0, 89.0, 95.0, 110.0})
    void should_ReturnTrue_When_Diet_Recommended_SIngleValue(Double coderWeight) {
        //given
        double weight = coderWeight;
        double height = 1.72;
        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        //then
        assertTrue(recommended);
    }

    @ParameterizedTest(name = "weight = {0}, height = {1}")
    @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
    void should_ReturnTrue_When_Diet_Recommended_MultipleValues(Double coderWeight, Double coderheight) {
        //given
        double weight = coderWeight;
        double height = coderheight;
        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        //then
        assertTrue(recommended);
    }

    @Test
    void should_ReturnFalse_When_Diet_Not_Recommended() {

        //Arrange act assert

        //given: initial conditions or input values
        double weight = 50.0;
        double height = 1.92;
        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);
        //then
        assertFalse(recommended);
    }

    @Test
    void should_ThrowArithmeticException_When_HeightZero() {
        //Arrange act assert

        //given: initial conditions or input values
        double weight = 50.0;
        double height = 0.0;
        // when
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
        //then
        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void should_ReturnCoderWithWorstBMI_when_CoderListNotEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.addAll(List.of(new Coder(1.80, 60.0),
                new Coder(1.82, 98.0),
                new Coder(1.82, 64.7)));
        // when

        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then

        assertEquals(1.82, coderWorstBMI.getHeight());
        assertEquals(98.0, coderWorstBMI.getWeight());
        /*
        assertEquals(1.85, coderWorstBMI.getHeight());
        assertEquals(98.5, coderWorstBMI.getWeight());
        */
    }


    @Test
    void should_ReturnCoderWithWorstBMIIn1Ms_when_CoderListHas10000Elements() {


        // given
        Assumptions.assumeTrue(this.environement.equals("prod"));
        List<Coder> coders = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            coders.add(new Coder(1.0 + i, 10.0 + i));
        }
        //when
    Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
        //then

        assertTimeout(Duration.ofMillis(500), executable);
    }

    //In the previous example if the first test fails than the second test will never be executed
    @Test
    void should_ReturnCoderWithWorstBMI_when_CoderListNotEmpty_ByTesting_AllAssertions() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.addAll(List.of(new Coder(1.80, 60.0), new Coder(1.82, 98.0), new Coder(1.82, 64.7)));
        // when

        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertAll(() -> assertEquals(1.82, coderWorstBMI.getHeight()), () -> assertEquals(98.0, coderWorstBMI.getWeight()));
    }

    @Test
    void should_ReturnNullWorstBMI_when_CoderListNotEmpty_ByTesting_AllAssertions() {
        // given
        List<Coder> coders = new ArrayList<>();
        // when

        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertNull(coderWorstBMI);
    }


    @Test
    void should_ReturnCorrectBMIScoreArray_when_CoderListNotEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.addAll(List.of(new Coder(1.80, 60.0), new Coder(1.82, 98.0), new Coder(1.82, 64.7)));
        double[] expected = {18.52, 29.59, 19.53};
        // when
        double[] bmiScores = BMICalculator.getBMIScores(coders);
        //then

        // basic comparison fails because the two object have different memory addresses
        //assertEquals(expected, bmiScores);


        assertArrayEquals(expected, bmiScores);
    }
}