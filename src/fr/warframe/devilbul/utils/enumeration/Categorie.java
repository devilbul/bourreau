package fr.warframe.devilbul.utils.enumeration;

public enum Categorie {
    Autre("Autre"),
    Help("Help"),
    Admin("Admin"),
    Supreme("Supreme Admin"),
    Modo("Modo"),
    Son("Son"),
    Information("Information"),
    Riven("Riven"),
    Sondage("Sondage"),
    Warframe("Warframe"),
    Alliance("Alliance"),
    Raid("Raid"),
    Troll("Troll"),
    Privee("Privee");

    private String categorie;

    Categorie(String category) {
        this.categorie = category;
    }

    @Override
    public String toString() {
        return this.categorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
