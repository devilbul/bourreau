package warframe.bourreau.util;

public class wfAPIUtil {

    public static String Boss(String str) {
        String boss = str.toLowerCase().split("boss_")[1];

        switch (boss) {
            case "1": /***/
                return "Captain Vor";
            case "4": /***/
                return "The Sergeant";
            case "8": /***/
                return "General Sargas Ruk";
            case "9": /***/
                return "Amboas";
            case "12": /***/
                return "Phorid";

            case "alad":
                return "Alad V";
            case "kela":
                return "Kela De Thaym";
            case "lephantis":
                return "Lephantis";
            case "hyena":
                return "Hyena Pack";
            case "hek":
                return "Councilor Vay Hek";
            case "jackal":
                return "Jackal";
            case "kril":
                return "Lieutenant Lech Kril";
            case "tyl":
                return "Tyl-Regor";
            case "raptor":
                return "Raptor";
            case "infalad":
                return "Mutalist Alad V";
            default:
                return "(boss à ajouter : " + boss + ")";
        }
    }

    public static String BossImage(String str) {
        switch (str) {
            case "Tyl-Regor":
                return "https://vignette1.wikia.nocookie.net/warframe/images/4/49/TylRegor.png";
            case "Captain Vor":
                return "https://vignette2.wikia.nocookie.net/warframe/images/6/6d/CaptainVorFK.png";
            case "Jackal":
                return "https://vignette4.wikia.nocookie.net/warframe/images/f/f3/JackalDE.png";
            case "Councilor Vay Hek":
                return "https://vignette3.wikia.nocookie.net/warframe/images/8/82/VayHekPortrait.png";
            case "The Sergeant":
                return "https://vignette2.wikia.nocookie.net/warframe/images/7/70/NefAnyoDE.png";
            case "Lieutenant Lech Kril":
                return "https://vignette4.wikia.nocookie.net/warframe/images/5/54/LechKril.png";
            case "Alad V":
                return "https://vignette2.wikia.nocookie.net/warframe/images/5/5f/DEAlad_V.png";
            case "Raptor":
                return "https://vignette3.wikia.nocookie.net/warframe/images/0/06/RaptorSeries.png";
            case "General Sargas Ruk":
                return "https://vignette2.wikia.nocookie.net/warframe/images/3/3c/GeneralSargasRukFK.png";
            case "Amboas":
                return "https://vignette4.wikia.nocookie.net/warframe/images/e/e1/CodexAmbulas.png";
            case "Kela De Thaym":
                return "https://vignette3.wikia.nocookie.net/warframe/images/b/ba/KelaDeThaym.png";
            case "Lephantis":
                return "https://vignette4.wikia.nocookie.net/warframe/images/3/3d/GolemFullAvatar.png";
            case "Phorid":
                return "https://vignette2.wikia.nocookie.net/warframe/images/a/a5/PhoridIcon.png";
            case "Hyena Pack":
                return "https://vignette2.wikia.nocookie.net/warframe/images/c/c2/DEHyenacombine.png";
            case "Mutalist Alad V":
                return "https://vignette2.wikia.nocookie.net/warframe/images/d/d7/InfestedAladV2.png";
            default:
                return null;
        }
    }

    public static String DefiPvpDaily(String mode, String str) {
        mode = mode.split("_")[1].toLowerCase();
        str = str.split("PVPTimedChallenge")[1].toLowerCase();

        switch (mode) {
            case "capturetheflag":
                return DefiCaptureTheFlag(str);
            case "speedball":
                return DefiSpeedBall(str);
            case "deathmatch":
                return DefiDeathMatch(str);
            case "teamdeathmatch":
                return DefiTeamDeathMatch(str);
            default:
                return null;
        }
    }

    public static String DefiPvpModeDaily(String mode) {
        mode = mode.split("_")[1].toLowerCase();

        switch (mode) {
            case "capturetheflag":
                return "Capture de céphalon";
            case "speedball":
                return "Lunaro";
            case "deathmatch":
                return "Annihilation";
            case "teamdeathmatch":
                return "Annihilation en équipe";
            default:
                return null;
        }
    }

    private static String DefiCaptureTheFlag(String str){
        switch (str) {
            // easy
            case "flagreturneasy":
                return "liberator :\nreturn your team's cephalons 1 times";
            case "matchcompleteeasy":
                return "see it throught :\ncomplete 1 matches";
            // medium
            case "flagcapturemedium":
                return "bandit :\ncapture 4 cephalons";
            case "matchcompletemedium":
                return "see it throught :\ncomplete 4 matches";
            // hard
            default:
                return "(défi à ajouter : " + str + ")";
        }
    }

    private static String DefiDeathMatch(String str) {
        switch (str) {
            // easy
            case "matchcompleteeasy":
                return "see it throught :\ncomplete 1 matches";
            case "killsstreakdominationeasy":
                return "domination :\nkill 1 enemies without them killing you";
            case "killsstreakstoppedeasy":
                return "streak stopped :\nkill 1 enemies on a kill streak";
            case "killstargetinaireasy":
                return "anti-air strike :\nkill 1 airborne enemies";
            case "killsheadshotseasy":
                return "shootist : \nget 1 headshot kills";
            case "killswhileslidingeasy":
                return "slide strike :\nkill 1 ennemies while sliding";
            // medium
            case "killspayback_medium":
                return "revenge :\nkill 3 enemies that have killed you";
            case "killsstreakstopped_medium":
                return "streak stopped :\nkill 3 enemies on a kill streak";
            case "killsmeleemedium":
                return "gladiator :\nget 4 melee kills";
            case "killspowermedium":
                return "void-painter :\nget 4 warframe power kills";
            // hard
            case "killswhileinairhard":
                return "focused air strike :\nkill 3 enemies while airbone in a match";
            case "killsstreakhard":
                return "focused kill streak :\ngo on 2 kill streaks in a match";
            case "killsstreakdominationhard":
                return "focused domintation :\nkill 3 ennemies without them killing you in a match";
            default:
                return "(défi à ajouter : " + str + ")";
        }
    }

    private static String DefiSpeedBall(String str) {
        switch (str) {
            // easy
            case "speedballcheckseasy":
                return "blockade :\nstrike opponents carrying the ball 3 times";
            case "speedballcatcheseasy":
                return "heads up :\ncatch 3 passes from teammates";
            case "speedballinterceptionseasy":
                return "interception :\nintercept 3 passes from opponents";
            // medium
            case "speedballchecksmedium":
                return "blockade :\nstrike opponents carrying the ball 10 times";
            case "speedballstealsmedium":
                return "snatch and grab :\nsteal the ball 6 times";
            case "speedballinterceptionsmedium":
                return "interception :\nintercept 6 passes from opponents";
            case "speedballpassesmedium":
                return "send it :\ncomplete 6 succesful passes to teammates";
            // hard
            case "speedballgoalshard":
                return "focused goal :\nscore 4 goals in a match";
            case "speedballcatcheshard":
                return ""; /**6*/
            case "speedballpasseshard":
                return "focused send it :\ncomplete 3 successful passes to teammates in a match";
            case "speedballinterceptionshard":
                return "focused interception :\nintercept 6 passes from opponents in a match";
            default:
                return "(défi à ajouter : " + str + ")";
        }
    }

    private static String DefiTeamDeathMatch(String str) {
        switch (str) {
            // easy
            case "killssecondaryeasy":
                return "secondary target :\nkill 1 enemies with your secondary weapons";
            case "killsprimaryeasy":
                return ""; /**1*/
            case "killsmeleeeasy":
                return "gladiator :\nget 1 melee kills";
            case "killspowereasy":
                return "void-painter :\nget 1 warframe power kill";
            // medium
            case "killsmeleemedium":
                return "gladiator :\nget 4 melee kills";
            case "killswhileinairmedium":
                return "air strike :\nkill 4 ennemies while airbone";
            case "killsprimarymedium":
                return "primary target :\nkill 4 enemies with your primary weapon";
            // hard
            case "killswhileinairhard":
                return "focused air strike :\nkill 3 enemies while airbone in a match";
            case "killstargetinairhard":
                return "focused anti-air strike :\nkill 3 airbone enemis in a match";
            case "killsmeleehard":
                return "focused gladiator :\nget 3 melee kills in a match";
            case "killssecondaryhard":
                return "focused secondary target :\n kill 3 enemies with your secondary weapon in a match";
            case "killswhileslidinghard":
                return "focused slide strike :\nkill 3 enemies while sliding in a match";
            default:
                return "(défi à ajouter : " + str + ")";
        }
    }

    public static String Conditions(String str) {
        String conditions = str.toLowerCase().split("modifier_")[1];

        switch (conditions) {
            case "1": /***/
                return "Low Gravity";
            case "2": /***/
                return "Fire";
            case "3": /***/
                return "Extreme Cold";
            case "63": /**corrosive*/
            case "64": /**electricity*/
            case "65": /**gas*/
            case "67": /**magnetic*/
            case "68": /**radiation*/
            case "69": /**toxin*/
            case "60": /**viral*/
                return "Enemy Elemental Enhancement : " + conditions;
            case "8": /***/
                return "Cryogenic Leakage";


            case "sniper_only":
            case "melee_only":
            case "bow_only": /***/
            case "shotgun_only": /***/
            case "secondary_only":
            case "rifle_only":
                return "Weapon Restriction : " + conditions.split("_")[0] + " only";
            case "armor":
            case "shields":
                return "augmented enemy " + conditions;
            case "hazard_fog":
                return "Environmental Hazard : dense fog";
            case "hazard_radiation":
                return "Environmental Hazard : radiation hazard";
            case "hazard_magnetic":
                return "Environmental Hazard : electromagnetic anomalies";
            case "eximus":
                return "Eximus Stronghold";
            case "low_energy":
                return "Energy Reduction ";
            case "slash":
            case "puncture":
            case "impact":
                return "Enemy Physical Enhancement: " + conditions;
            case "explosion":
                return "Enemy Elemental Enhancement : blast";
            case "fire":
                return "Enemy Elemental Enhancement : heat";
            case "freeze":
                return "Enemy Elemental Enhancement : cold";
            default:
                return "(conditions à ajouter : " + conditions + ")";
        }
    }

    public static String TypeFissure(String str) {
        String Tn = str.toLowerCase();

        switch (Tn) {
            case "voidt1":
                return "Lith";
            case "voidt2":
                return "Meso";
            case "voidt3":
                return "Neo";
            case "voidt4":
                return "Axi";
            default:
                return null;
        }
    }

    public static String TypeMission(String str) {
        String mt = str.toLowerCase().split("t_")[1];

        switch (mt) {
            case "intel":
                return "Spy";
            case "rescue":
                return "Rescue";
            case "defense":
                return "Defense";
            case "capture":
                return "Capture";
            case "territory":
                return "Interception";
            case "mobile_defense":
                return "Mobile defense";
            case "extermination":
                return "Extermination";
            case "retrieval":
                return "Hijack";
            case "survival":
                return "Survival";
            case "excavate":
                return "Excavation";
            case "hive":
                return "Hive";
            case "sabotage":
                return "Sabotage";
            case "assassination":
                return "Assassination";
            default:
                return null;
        }
    }

    public static String TypeReward(String str) {
        switch (str) {
            case "ChemComponent":
                return "Detonite Injector";
            case "EnergyComponent":
                return "Fieldron";
            case "BioComponent":
                return "Mutagen Mass";
            default:
                return null;
        }
    }

    public static String TypeRewardAlert(String str) {
        switch (str) {
            case "Alertium":
                return "Nitain Extract";
            case "VoidTearDrop":
                return "Void Traces";
            case "ArgonCrystal":
                return "Argon Crystal";
            default:
                return str;
        }
    }
}
