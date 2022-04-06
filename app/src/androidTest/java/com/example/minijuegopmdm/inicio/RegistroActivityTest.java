package com.example.minijuegopmdm.inicio;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.minijuegopmdm.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegistroActivityTest {

    @Rule
    public ActivityTestRule<RegistroActivity> pruebaRegistro = new ActivityTestRule(RegistroActivity.class);

    @Test
    public void registroCorrecto() {

        onView(withId(R.id.edtxt_nombre2)).perform(typeText("paloma"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edtxt_pass2)).perform(typeText("12345678"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.edtxt_pass3)).perform(typeText("12345678"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.btn_registro2)).perform(click());

        onView(withText("Registro guardado")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        //onView(withText(endsWith("Registro guardado"))).inRoot(withDecorView(not(is(pruebaRegistro.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }


}