package warframe.bourreau.util;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class WaitingSound {

    private MessageReceivedEvent event;
    private String commandeSon;
    private String idDemandeur;

    public WaitingSound(MessageReceivedEvent event, String commandeSon) {
        this.event = event;
        this.commandeSon = commandeSon;
        this.idDemandeur = event.getAuthor().getId();
    }

    public MessageReceivedEvent getEvent() { return event; }
    public String getCommandeSon() { return commandeSon; }
    public String getIdDemandeur() { return idDemandeur; }
    public void setEvent(MessageReceivedEvent event) { this.event = event; }
    public void setCommandeSon(String commandeSon) { this.commandeSon = commandeSon; }
    public void setIdDemandeur(String idDemandeur) { this.idDemandeur = idDemandeur; }
}
