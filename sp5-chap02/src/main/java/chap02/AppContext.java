package chap02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //@Configuration 은 해당 클래스를 스프링 설정 클래스로 지정한다.
public class AppContext {

  @Bean
  public Greeter greeter() {
    Greeter greeter = new Greeter();
    greeter.setFormat("%s, 안녕하세요?!");
    return greeter;
  }
  /*
  * 스프링은 객체를 생성하고 초기화하는 기능을 제공하는데, 9 ~ 14 행의 코드가 한 개의 객체를 생성하고 초기화하는 설정을 담고 있다.
  * 스프링이 생성하는 객체를 빈(Bean) 객체라고 부르는데, 이 빈 객체에 대한 정보를 담고 있는 메서드가 greeter() 메서드이다.
  * 이 메서드에는 @Bean 어노테이션이 붙어 있다. 해당 어노테이션을 메서드에 붙이면 메서드가 생성한 객체를 스프링이 관리하는 빈 객체로 등록한다.
  *
  * @Bean 어노테이션을 붙인 메서드의 이름은 빈 객체를 구분할 때 사용한다. 예를 들어 스프링이 9 ~ 14 행에서 생성한 객체를 구분할 때 메서드의 이름인 greeter 를 사용한다
  * 이 이름은 빈 객체를 참조할 때 사용된다.
  *
  * 이렇게 Bean 객체에 대한 생성과 초기화 설정을 다 작성했다면 이제 스프링이 제공하는 클래스를 이용해, AppContext 를 읽어와 사용하면 된다.
  *
  * */

}
