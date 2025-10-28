package portaleventos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class PedidosManager {
    private SessionFactory sessionFactory;

    public void setup() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException("Erro ao criar SessionFactory", ex);
        }
    }

    public void exit() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public void create() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // --- Create Students ---
        Student s1 = new Student();
        s1.setName("Diogo");
        s1.setYear(1);
        s1.setEmail("11234@upt.pt");
        Student s2 = new Student();
        s2.setName("Bia");
        s2.setYear(2);
        s2.setEmail("bia23@gmail.com");

        session.persist(s1);
        session.persist(s2);
        session.getTransaction().commit();
        session.close();
        System.out.println("Pedido guardado com sucesso!");
    }


    public void update()
    {
        // code to modify a course
        Course c = new Course();
        c.setId(1);
        c.setTitle("Java Course");
        c.setEcts(4);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(c);
        session.getTransaction().commit();
        session.close();

    }
    public void delete()
    {
        // code to remove student with id = 2
        Student s = new Student();
        s.setId(2);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(s);
        session.getTransaction().commit();
        session.close();
    }

    public void courseDepartments() {
        // begin transaction
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Create a Department object
        Department dep = new Department();
        dep.setDepartName("DCT");

        // Create two courses
        Course c1 = new Course();
        c1.setTitle("GenAI");
        c1.setEcts(6);

        Course c2 = new Course();
        c2.setTitle("SQL server");
        c2.setEcts(3);

        // associate the courses with the department
        dep.addCourse(c1);
        dep.addCourse(c2);

        // Save the Department (this will also save the associated courses
        // due to cascadeType.ALL)
        session.persist(dep);

        // Commit the transaction
        session.getTransaction().commit();
        session.close();
    }

    public void addSudentsCourses() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

// get students with id =1 and id =2
        long studentId = 1;
        Student s1 = session.get(Student.class, studentId);

        studentId = 2;
        Student s2 = session.get(Student.class, studentId);

        // get the course with id =2
        long courseId = 2;
        Course c = session.get(Course.class, courseId);

        // --- Associate students to course ---
        c.addStudents(s1);
        c.addStudents(s2);
        session.persist(c);
        session.getTransaction().commit();
        session.close();
    }
