package fr.warframe.devilbul.utils.annotations.help;

import fr.warframe.devilbul.utils.enumeration.Categorie;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Help {
    String field();

    Categorie categorie() default Categorie.Autre;
}
