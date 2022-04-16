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
 * 이렇게 의존하는 객체를 직접 생성하는 방식 외에
 *
 *
 *
 *
 * */