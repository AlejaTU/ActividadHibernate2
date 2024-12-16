package models;

import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TicketDao {

    //crear un nuevo ticket
    public void createTicket(Ticket ticket) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSession()) {
        transaction = session.beginTransaction();
        session.persist(ticket); //para guardar el ticket
        transaction.commit();
        System.out.println("Ticket creado correctamente " + ticket.getNombreAtraccion());

    } catch (Exception e) {

        System.out.println("Error al crear el ticket "  + e.getMessage());
    }
    }



    //lista de tickets
    public List<Ticket> getAllTickets() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM Ticket", Ticket.class).list();
        } catch (Exception e) {
            System.out.println("Error al consultar los tickets " + e.getMessage());
            return null;
        }

    }

    //
    public Ticket getTicketById(int ticketId) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(Ticket.class, ticketId); // Busca el ticket por ID
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Devuelve null si ocurre un error o no se encuentra
        }
    }



    // Actualizar un ticket
    public void updateTicket(int ticketId, String nuevoNombreAtraccion, double nuevoPrecio) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, ticketId);
            if (ticket != null) {
                ticket.setNombreAtraccion(nuevoNombreAtraccion);
                ticket.setPrecio(nuevoPrecio);
                session.merge(ticket);
                transaction.commit();
                System.out.println("Ticket actualizado correctamente: " + ticket.getNombreAtraccion());
            } else {
                System.out.println("No se encontró el ticket con ID: " + ticketId);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }




    //eliminar un ticket por id
    public void deleteTicket(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            if (ticket != null) {
                session.remove(ticket); // Eliminar el ticket
                transaction.commit();
                System.out.println("Ticket eliminado correctamente: " + ticket.getNombreAtraccion());
            } else {
                System.out.println("No se encontró el ticket con ID: " + id);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    //los tickets en relacion a un usuario por id
    public List<Ticket> getTicketsByUserId(int userId) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                            "FROM Ticket t WHERE t.user.id = :userId", Ticket.class)
                    .setParameter("userId", userId) // Pasamos el ID del usuario
                    .list(); // Ejecutamos la consulta y devolvemos la lista
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Si algo falla, devolvemos null
        }
    }


    //buscar los ticket segun atraccion
    public List<Ticket> getTicketsByAttractionName(String attractionName) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                            "FROM Ticket t WHERE t.nombreAtraccion = :attractionName", Ticket.class)
                    .setParameter("attractionName", attractionName)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //gasto de un suuario en las atracciones
    public Double getAverageSpendingByUserId(int userId) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                            "SELECT AVG(t.precio) FROM Ticket t WHERE t.user.id = :userId", Double.class)
                    .setParameter("userId", userId)
                    .uniqueResult(); //solo mostrar un resultado
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    //gasto total por usuario
    public Double getTotalSpendingByUserId(int userId) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                            "SELECT SUM(t.precio) FROM Ticket t WHERE t.user.id = :userId", Double.class)
                    .setParameter("userId", userId) // Pasamos el ID del usuario
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Devuelve null si ocurre algo
        }
    }


}
