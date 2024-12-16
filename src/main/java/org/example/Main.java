package org.example;

import models.Ticket;
import models.TicketDao;
import models.UserDao;
import models.User;


import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        HibernateUtil.getSession();

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");


        //crear usuariodao
        UserDao userDao = new UserDao();

        //leemos los usuarios
        leerUsuarios(userDao);



        //crear usuario nuevo
        User user = new User();
        user.setNombre("Antonia Perez");

        //guardamos el usuario
        userDao.createUser(user);


        //leemos otra vez todos
        leerUsuarios(userDao);


        //leemos al usuario por id
        int userId = 4;
        buscarUsuarioPorId(userDao, userId);

        //actualizar usuario
        //crear primero nuevo nombre
        User userToUpdate = userDao.getUserById(4);
        actualizarUser(userDao, userToUpdate, "CAMILO GRILLO");


        //leemos otra vez todos
        leerUsuarios(userDao);

        //eliminar usuario
        eliminarUsuario(userDao, 7);


        //leemos otra vez todos
        leerUsuarios(userDao);


        //crear ticketdao
        TicketDao ticketDao = new TicketDao();

        //lista de tickets
        leerTickets(ticketDao);

        //crear ticket
        Ticket ticket = new Ticket();
        ticket.setNombreAtraccion("Jaguar");
        ticket.setPrecio(40.5);
        ticket.setUser(userDao.getUserById(2));
        ticketDao.createTicket(ticket);

        //lista de tickets
        leerTickets(ticketDao);

        //actualizar un ticket
        String nombre = "Mansion de la tia Agueda";
        double nuevoPrecio = 10.0;
        ticketDao.updateTicket(9, nombre,nuevoPrecio);

        //lista de tickets
        leerTickets(ticketDao);

        ticketDao.deleteTicket(17);
        ticketDao.deleteTicket(18);


        //obtener tickets del usuario 2
        int userID = 2;
        ticketporUsuario(ticketDao, userID);


        //obtener ticket por atraccion
        String attractionName = "Carrusel";
        obtenerTicketsPorAtraccion(ticketDao, attractionName);


        //gasto promedio de un usuario
        int userIds = 2;
        double avgSpending = ticketDao.getAverageSpendingByUserId(userIds);
        System.out.println("Gasto promedio del usuario con ID " + userIds + ": $" + avgSpending);


        //gasto total de un usuario
        int usuarioTotal = 2;
        mostrarGastoTotalUsuario(ticketDao, usuarioTotal);


    }

    private static void mostrarGastoTotalUsuario(TicketDao ticketDao, int usuarioTotal) {
        Double total = ticketDao.getTotalSpendingByUserId(usuarioTotal);
        if(total != null) {
            System.out.println("El gasto total del usuario con id " + usuarioTotal + ":  " + total + " euros");
        } else {
            System.out.println("No se encontraron tickets para este usuario");
        }
    }

    private static void obtenerTicketsPorAtraccion(TicketDao ticketDao, String attractionName) {
        List<Ticket> attractionTickets = ticketDao.getTicketsByAttractionName(attractionName);

        if (attractionTickets != null && !attractionTickets.isEmpty()) {
            System.out.println("Tickets para la atracción " + attractionName + ":");
            for (Ticket t : attractionTickets) {
                System.out.println("- Usuario ID: " + t.getUser().getId() + " ($" + t.getPrecio() + ")");
            }
        } else {
            System.out.println("No se encontraron tickets para la atracción: " + attractionName);
        }

    }

    private static void ticketporUsuario(TicketDao ticketDao, int userId) {
        List<Ticket> tickets = ticketDao.getTicketsByUserId(userId);

        if (tickets != null && !tickets.isEmpty()) {
            System.out.println("Tickets del usuario con ID " + userId + ":");
            for (Ticket t : tickets) {
                System.out.println("- Atracción: " + t.getNombreAtraccion() + " ($" + t.getPrecio() + ")");
            }
        } else {
            System.out.println("No se encontraron tickets para el usuario con ID: " + userId);
        }
    }



    private static void leerTickets(TicketDao ticketDao) {
        System.out.println("Lista de Tickets");
        List<Ticket> tickets = ticketDao.getAllTickets();
        if(tickets != null && !tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                System.out.println("- " + ticket.getNombreAtraccion() + " ID: " + ticket.getId());
            }
        }

    }

    public static void leerUsuarios(UserDao userDao) {
        System.out.println("Lista de todos los usuarios:");
        List<User> users = userDao.getAllUsers();

        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                System.out.println("- " + user.getNombre() + " Id:" + user.getId());
            }
        } else {
            System.out.println("No hay usuarios registrados.");
        }
    }

    // buscar por id
    public static void buscarUsuarioPorId(UserDao userDao, int userId) {
        User user = userDao.getUserById(userId);

        if (user != null) {
            System.out.println("Usuario encontrado: " + user.getNombre());
        } else {
            System.out.println("No se encontró el usuario con ID: " + userId);
        }
    }


    //metodo para actualizar
    public static void actualizarUser(UserDao userDao, User user, String nuevoNombre) {
        user.setNombre(nuevoNombre);
        //lamamos al dao para actualizar el nuevo nombre
        userDao.updateUser(user);
        System.out.println("El usuario ha sido actualizado correctamente a: " + nuevoNombre);

    }

    //metodo para eliminar usuario
    public static void eliminarUsuario(UserDao userDao, int userId) {
        userDao.deleteUser(userId);
        System.out.println("EL usuario con id " + userId + " ha sido eliminado ");
    }
}