package dong.ognl;

/**
 * @author DONGSHILEI
 * @create 2019-12-10 17:52
 * @since 1.0
 */
public class Person {
    private String name;
    private Dog dog;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
