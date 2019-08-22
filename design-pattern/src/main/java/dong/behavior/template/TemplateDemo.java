package dong.behavior.template;

/**
 * @author DONGSHILEI
 * @create 2019-08-21 20:22
 * @since 1.0
 */
public class TemplateDemo {

    public static void main(String[] args) {
        Game cricket = new Cricket();
        cricket.play();
        Game football= new Football();
        football.play();
    }
}
