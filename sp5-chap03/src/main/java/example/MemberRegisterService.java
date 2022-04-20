package example;

public class MemberRegisterService {

  private MemberDao memberDao = new MemberDao();

  public void regist(RegisterRequest req) throws DuplicateMemberException {
    //이메일로 회원 데이터 조회
    Member member = memberDao.selectByEmail(req.getEmail());

    if (member != null) {
      // 같은 이메일을 가진 회원이 이미 존재하면 익셉션 발생
      throw new DuplicateMemberException("dup email" + req.getEmail());
    }
    //같은 이메일을 가진 회원이 존재하지 않으면 DB 에 삽입
    Member newMember = new Member();
    memberDao.insert(newMember);
  }

}

/** ◆ 스프링 DI
 *
 * 1. 의존이란?
 *
 * DI 는 'Dependency Injection' 의 약자로 우리말로는 '의존 주입' 이라고 번역한다. 이 단어의 의미를 이해하려면 먼저
 * '의존(dependency)'이 무엇인지 먼저 알아야 한다. 여기서 말하는 의존은 객체 간의 의존을 의미한다. 이해를 돕기 위해
 * 회원가입을 처리하는 기능을 구현한 위의 코드를 보자.
 *
 * 서로 다른 회원은 동일한 이메일 주소를 사용할 수 없다는 요구사항이 있다고 가정해보자. 이 제약사항을 처리하기 위해
 * MemberRegisterService 클래스는 MemberDao 객체의 selectByEmail() 메서드를 이용해서 동일한 이메일을 가진 회원 데이터가 존재하는지
 * 확인한다. 만약 같은 이메일을 가진 회원 데이터가 존재한다면 위 코드처럼  익셉션을 발생시킨다. 같은 이메일을 가진 회원 데이터가 존재하지
 * 않으면 회원 정보를 담은 Member 객체를 생성하고 MemberDao 객체의 insert() 메서드를 이용해서 DB 에 회원 데이터를 삽입한다.
 *
 * 여기서 눈여겨 볼 점은 MemberRegisterService 클래스가 DB 처리를 위해 MemberDao 클래스의 메서드를 사용한다는 것이다.
 * 회원데이터가 존재하는지 확인하기 위해 MemberDao 객체의 selectByEmail() 메서드를 실행하고, 회원 데이터를 DB 에 저장하기
 * 위해 insert() 메서드를 실행한다.
 *
 * 이렇게 한 클래스가 다른 클래스의 메서드를 실행할 때 이를 '의존' 한다고 표현한다.
 * 앞서 코드에서는 'MemberRegisterService 클래스가 MemberDao 클래스에 의존한다' 고 표현할 수 있다.
 *
 * ※ 의존은 변경에 의해 영향을 받는 관계를 의미한다. 예를 들어 MemberDao 의 insert() 메서드의 이름을 insertMember() 로 변경하면
 * 이 메서드를 사용하는 MemberRegisterService 클래스의 소스 코드도 함께 변경된다. 이처럼 변경에 따른 영향이 전파되는 관계 '의존' 한다고 표현한다.
 *
 * 의존하는 대상이 있으면 당연히 그 대상을 구하는 방법이 필요하다. 가장 쉬운 방법은 의존 대상 객체를 직접 생성하는 것이다.
 * 앞서 살펴본 MemberRegisterService 클래스도 다음 코드처럼 의존 대상인 MemberDao 의 객체를 직접 생성해서 필드에 할당했다.
 *
 * → private MemberDao memberDao = new MemberDao();
 *
 * MemberRegisterService 클래스에서 의존하는 MemberDao 객체를 직접 생성하기 때문에 MemberRegisterService 객체를 생성하는 순간에 MemberDao 객체도 함께 생성된다.
 * 클래스 내부에서 직접 의존 객체를 생성하는 것이 쉽긴 하지만 유지보수 관점에서 문제점을 유발할 수 있다. 이 문제점에 대해서는 뒤에서 다시 살펴보도록 하고
 * 이렇게 의존하는 객체를 직접 생성하는 방식 외에 의존 객체를 구하는 또 다른 방법이 있다. 그 방법은 DI 와 서비스 로케이터이다. 이 중 스프링과 관련된 것은
 * DI 로서 DI 에 대해서만 설명한다.
 *
 *
 *
 * 2. DI 를 통한 의존 처리
 *
 * DI(Dependency Injection, 의존 주입) 은 의존하는 객체를 직접 생성하는 대신 의존 객체를 전달받는 방식을 사용한다. 예를 들어 앞서 의존 객체를 직접 생성한
 * MemberRegisterService 클래스에 대한 DI 방식을 적용하면 다음처럼 할 수 있다.
 *
 * public class MemberRegisterService{
 *
 *  private MemberDao memberDao;
 *
 *  public MemberRegisterService(MemberDao memberDao) {
 *    this.memberDao = memberDao;
 *  }
 *
 *  ...
 *
 * }
 *
 * 직접 의존 객체를 생성했던 코드와 달리 바뀐 코드는 의존 객체를 직접 생성하지 않는다. 대신 생성자를 통해서
 * 의존 객체를 전달 받는다. 즉, 생성자를 통해 MemberRegisterService 클래스가 의존(Dependency)하고 있는
 * MemberDao 객체를 주입(injection) 받은 것이다. 의존 객체를 직접 구하지 않고 생성자를 통해서 전달받기 때문에
 * 이 코드는 DI (의존 주입) 패턴을 따르고 있다.
 *
 * DI 를 적용한 결과 MemberRegisterService 클래스를 사용하는 코드는 다음과 같이 MemberRegisterService 객체를 생성할 때
 * 생성자에 MemberDao 객체를 전달해야 한다.
 *
 * MemberDao memberDao = new MemberDao();
 * MemberRegisterService mrs = new MemberRegisterService(memberDao);
 * //의존 객체를 생성자를 통해 전달 받는다.
 *
 * 언뜻 보았을 때는 뭔가 더 복잡해 보인다. 앞서 의존 객체를 직접 생성하는 방식과 달리 의존 객체를 주입하는 방식은 객체를 생성하는 부분의
 * 코드가 조금 더 길어졌다. 그냥 직접 의존 객체를 생성하면 되는데 왜 굳이 생성자를 통해 의존하는 객체를 주입하는걸까?
 * 이 이유를 이해하기 위해서는 객체 지향 설계에 대한 기본적인 이해가 필요한데, 이제 막 개발에 입문한 개발자에겐 다소 어려운 개념이 될 수 있다.
 * 간단히 이유를 말하면 그것은 바로 '변경의 유연함' 때문이다.
 *
 *
 * 3. DI 와 의존 객체 변경의 유연함
 *
 * 의존 객체를 직접 생성하는 방식은 필드나 생성자에서 new 연산자를 이용해서 객체를 생성한다. 회원 등록 기능을 제공하는 MemberRegisterService 클래스에서
 * 다음 코드처럼 의존 객체를 직접 생성할 수 있다.
 *
 * public class MemberRegisterService {
 *  private MemberDao memberDao = new MemberDao();
 * }
 *
 * 회원 암호 변경 기능을 제공하는 ChangePasswordService 클래스도 다음과 같이 의존 객체를 직접 생성한다고 하자.
 *
 * public class ChangePasswordService {
 *   private MemberDao memberDao = new MemberDao();
 * }
 *
 * MemberDao 클래스는 회원 데이터를 데이터베이스에 저장한다고 가정해보자. 이 상태에서 회원 데이터의 빠른 조회를 위해 캐시를 적용해야 하는 상황이 발생했다.
 * 그래서 MemberDao 클래스를 상속받은 CachedMemberDao 클래스를 만들었다.
 *
 * public class CachedMemberDao extends MemberDao {
 *   ...
 * }
 *
 * 캐시 기능을 적용한 CachedMemberDao 를 사용하려면 MemberRegisterService 클래스와 ChangePasswordService 클래스의 코드를 다음과 같이 변경해주어야 한다.
 *
 *  private MemberDao memberDao = new MemberDao(); → private MemberDao memberDao = new CachedMemberDao();
 *
 * 만약 MemberDao 객체가 필요한 클래스가 세 개라면 세 클래스 모두 동일하게 소스 코드를 변경해야 한다.
 *
 * 동일한 상황이에서 DI 를 사용하면 수정할 코드가 줄어든다. 다음과 같이 직접 객체 생성이 아니라 생성자를 통해 의존 객체를 주입받는다고 한다면
 *
 *
 * */