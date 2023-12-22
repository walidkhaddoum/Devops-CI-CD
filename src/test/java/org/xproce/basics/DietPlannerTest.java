package org.xproce.basics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xproce.Coder;
import org.xproce.DietPlan;
import org.xproce.DietPlanner;
import org.xproce.Gender;

import static org.junit.jupiter.api.Assertions.*;

class DietPlannerTest {

    private DietPlanner dietPlanner;

    @BeforeEach
    void setup() {
        this.dietPlanner = new DietPlanner(20, 30, 50);
    }

    @AfterEach
    void afterEach() {
        System.out.println("A unit test was finiched");
    }

    @Test
    void should_ReturnCorrectDiet_Plan_when_CorrectCoder() {
// given
        Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
        DietPlan expected = new DietPlan(2202, 110, 73, 275);
// when
        DietPlan actual = dietPlanner.calculateDiet(coder);

        // then

        // the test fails for the same reason as the array test
        // assertEquals(expected, actual);

        assertAll(
                () -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate()),
                () -> assertEquals(expected.getFat(), actual.getFat()),
                () -> assertEquals(expected.getProtein(), actual.getProtein()),
                () -> assertEquals(expected.getCalories(), actual.getCalories())
        );
    }

    @Test
    void checkTheschedule(){
        System.out.println("Hello world");
    }
}