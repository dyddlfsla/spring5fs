package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {

    /**
    * AnnotationConfigApplicationContext 클래스는 자바 설정에서 정보를 읽어와 빈 객체를 생성하고 관리한다.
    * AnnotationConfigApplicationContext 객체를 생성할 때 앞서 작성한 AppContext 클래스를 생성자 파라미터로 전달한다.
    * 즉 AppContext 에서 정의한 @Bean 설정 정보를 읽어와 Greeter 객체를 생성하고 초기화한다.
    *  */
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
    Greeter greeter = ctx.getBean("greeter", Greeter.class);
    /**
    * getBean() 메서드는 AnnotationConfigApplicationContext 가 자바 설정을 읽어와 생성한 빈(bean) 객체를 검색할 때 사용한다.
    * getBean() 메서드의 첫 번째 파라미터는 @Bean 애노테이션의 메서드 이름인 빈 객체의 이름이며, 두 번째 파라미터는 검색할
    * 빈 객체의 타입이다. 앞서 작성한 자바 설정(AppContext)를 보면 @Bean 메서드의 이름이 'greeter' 이고 생성한 객체의 리턴타입이 Greeter 이므로,
    * 위 코드의 getBean()는 greeter() 메서드가 생성한 Greeter 객체를 리턴한다.
    * */
    String msg = greeter.greet("스프링");
    /*
    * 가져온 빈 객체의 greeter() 메서드를 실행하고 있다.
    * */

    System.out.printf("%s%n", msg);
    ctx.close();
  }

  /**
   *  간단한 스프링 프로그램인 Main 을 작성하고 실행해봤다. 이 코드에서 핵심은 AnnotationConfigApplicationContext 클래스이다.
   *  스프링의 핵심 기능은 객체를 생성하고 초기화 하는 것이다. 이와 관련된 기능은 ApplicationContext 라는 인터페이스에 정의되어 있다.
   *  AnnotationConfigApplicationContext 클래스는 저 인터페이스를 알맞게 구현한 클래스 중 하나이다. AnnotationConfigApplicationContext 클래스는
   *  자바 클래스에서 정보를 읽어와 객체 생성과 초기화를 수행한다. XML 파일이나 그루비 설정 코드를 이용해서 객체 생성/초기화를 수행하는 클래스도 존재한다.
   *
   *  대표적으로
   *  1) AnnotationConfigApplicationContext : 자바 애노테이션을 이용한 클래스로부터 객체 설정 정보를 가져온다.
   *  2) GenericXmlApplicationContext: XML 로부터 객체 설정 정보를 가져온다.
   *  3) GenericGroovyApplicationContext: 그루비 코드를 이용해 설정 정보를 가져온다.
   *
   *  어떤 구현 클래스를 사용하든, 각 구현 클래스는 설정 정보로부터 빈(Bean) 이라고 불리는 객체를 생성하고 그 객체를 내부에 보관한다.
   *  그리고 getBean() 메서드를 실행하면 해당하는 빈 객체를 제공한다.
   *
   *  ApplicationContext(또는 BeanFactory)는 빈 객체의 생성, 초기화, 보관, 제거 등을 관리하고 있어서 ApplicationContext 를 컨테이너(Container) 라고도 부른다.
   *  이 책에서도 ApplicationContext 나 BeanFactory 등을 스프링 컨테이너라고 표현할 것이다.
   *
   * */

}
