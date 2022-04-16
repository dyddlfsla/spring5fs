package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
    Greeter greeter1 = ctx.getBean("greeter", Greeter.class);
    Greeter greeter2 = ctx.getBean("greeter", Greeter.class);

    System.out.printf("(greeter1 == greeter2) = %b%n", greeter1 == greeter2 );
    ctx.close();

    /**
     * 위의 결과는 어떻게 될까? 실제로 실행해보면 greeter1 == greeter2 의 결과가 true 로 출력되는 것을 확인할 수 있다.
     * greeter1 == greeter2 가 true 라는 것은 greeter1 과 greeter2 가 같은 객체라는 것을 의미한다. 즉 getBean() 메서드가
     * 같은 객체를 리턴하는 것이다.
     *
     * 별도의 설정을 하지 않은 경우 스프링은 한 개의 빈 객체만을 생성하며, 이 때 빈 객체는 '싱글톤(Singleton)' 범위를 갖는다고 한다.
     * 싱글톤은 단일 객체(single object) 를 의미하는 단어로서 스프링은 기본적으로 한 개의 @Bean 어노테이션에 대해 한 개의 빈 객체를 생성한다.
     * 따라서 만약 다음과 같은 설정을 사용하면 "greeter1" 에 해당하는 객체 한 개와 "greeter2" 에 해당하는 객체 한 개. 이렇게 두 개의 빈 객체를 생성된다.
     *
     * @Bean
     * public Greeter greeter1() {
     *   Greeter greeter = new Greeter();
     *   greeter.setFormat("%s, 안녕하세요!");
     *   return greeter;
     * }
     *
     * @Bean
     * public Greeter greeter2() {
     *  Greeter greeter = new Greeter();
     *  greeter.setFormat("%s, 안녕하세요!");
     *  return greeter;
     * }
     *
     * */
  }

}
