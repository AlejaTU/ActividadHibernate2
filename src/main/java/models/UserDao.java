package models;

import org.example.HibernateUtil;
import org.hibernate.Session;
import models.User;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {

    //Leer to-dos los users que hay
    public  List<User> getAllUsers() {
        //creamos la sesion
        try (Session session = HibernateUtil.getSession()) {
            //hacemos una consulta hql de la tabla user
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //crear usuario
    public void createUser(User user) {
        //decalrar la transaccion para agrupar la operacion de persistencia
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            //persistir al usuario e sdecir guardarlo
            session.persist(user);

            //confirmar la transaccion
            transaction.commit();
            System.out.println("USuario creado correctamente: " + user.getNombre());
        } catch (Exception e) {
            System.out.println("Se ha producido un error al crear el usuario " + e.getMessage());
        }
    }


    //leer usuario por id
    public User getUserById(int id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(User.class, id);// para buscar el usuario con el id que se indique

        } catch (Exception e) {
            System.out.println("no se ha podido leer el usuario por id " + e.getMessage());
            return null;
        }
    }

    //actualizar usuario
    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(user); // actauliza la entidad en la base de datos
            transaction.commit();
            System.out.println("Usuario actualizado correctamente: " + user.getNombre());
        } catch (Exception e) {
            System.out.println("El usuario no se ha actualizado bien: " + e.getMessage());
        }
    }


    //eliminar usuario

    public void deleteUser(int userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
           transaction = session.beginTransaction();

           //buscamos al usuario por id
            User user = getUserById( userId);
            if(user != null) {
                session.remove(user);
                transaction.commit();
                System.out.println("Usuario eliminado correctamente: " + user.getNombre());

            } else {
                System.out.println("El usuario no existe");
            }

        } catch (Exception e) {
            System.out.println("Error a la hora de eliminar el usuario " + e.getMessage());
        }
    }
}
