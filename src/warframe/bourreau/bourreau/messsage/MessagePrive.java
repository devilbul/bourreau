package warframe.bourreau.messsage;

public class MessagePrive {

    /*private static void envoiMessage(Member destinataire, Message message) { destinataire.getUser().openPrivateChannel().complete().sendMessage(message).queue(); }

    public static void messageReclamationAdmin(PrivateMessageReceivedEvent event) {
        Role admin = getJda().getGuildById(serveurID).getRoleById(adminID);
        List<Member> listAdmin = getJda().getGuildById(serveurID).getMembersWithRoles(admin);
        MessageBuilder reclamation = new MessageBuilder();

        reclamation.append(event.getAuthor());
        reclamation.append(" a fait une réclamation.\n\nmessage :   ").append(event.getMessage().getContentDisplay().replaceFirst(" ", "@").split("@")[1]);

        for (Member aListAdmin : listAdmin) {
            if (!aListAdmin.getUser().equals(event.getJDA().getSelfUser()))
                EnvoiMessage(aListAdmin, reclamation.build());
        }
    }

    public static void messageRecrutement(PrivateMessageReceivedEvent event) {
        Role admin = getJda().getGuildById(serveurID).getRoleById(adminID);
        List<Member> listAdmin = getJda().getGuildById(serveurID).getMembersWithRoles(admin);
        MessageBuilder reclamation = new MessageBuilder();

        reclamation.append(event.getAuthor());
        reclamation.append(" souhaite rejoindre l'Alliance.\n\nmessage :   ").append(event.getMessage().getContentDisplay().replaceFirst(" ", "@").split("@")[1]);

        for (Member aListAdmin : listAdmin) {
            if (!aListAdmin.getUser().equals(event.getJDA().getSelfUser()))
                EnvoiMessage(aListAdmin, reclamation.build());
        }
    }*/
}
