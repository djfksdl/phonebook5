package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.PhonebookService;
import com.javaex.vo.PersonVo;
@Controller
public class PhonebookController {
	//필드
	//메모리에 올려줘문법 :@Autowired
	@Autowired
	private PhonebookService phonebookService;
	
	//생성자-디폴트 생성자 쓸거임
	
	//메소드-gs
	
	//메소드-일반
	
	//등록폼
	//localhost:8080/phonebook5(여기까진 공통주소)/phone/writeform
	@RequestMapping(value="/phone/writeform" ,method={RequestMethod.GET , RequestMethod.POST } )//get으로 실행시켜줘야한다. 하나만 쓰면 method= RequestMethod.GET으로 쓴다.지금은 연습하느라 두개 다 하는걸로 써놓음/ 이거 쓰는 이유는 매핑시키려고?? 2:54
	public String writeForm() {
		System.out.println("PhonebookController.writeForm()");
//		return "/WEB-INF/views/writeForm.jsp"; //신한테 알려주는 역할을 함. /이건 포워드 하는 문법 
		return "writeForm";//'뷰' 를 추가해줘서 앞뒤에 붙은 건 빼줘야 주소가 제대로 찾아감
	}
	//등록2
	@RequestMapping(value="/phone/write2" ,method={RequestMethod.GET , RequestMethod.POST } )
	//localhost:8080/phonebook5/phone/write?name=황일영&hp=010&company=02
	public String write(@ModelAttribute PersonVo personVo)  {
		System.out.println("PhonebookController.write2()");
		//System.out.println(name + hp + company);
		
		//vo묶는다
		//PersonVo personVo = new PersonVo(name ,hp, company );
		System.out.println(personVo.toString());
		
		//서비스를 메모리에 올리고 서비스의 메소드 사용해야함!(3단계)
//		PhonebookService phonebookService = new PhonebookService(); -> spring처럼 쓰기 위해 컨트롤러가 new하지 않고 필드에 놓고 문법 +해서 자동연결한다.
		phonebookService.exeWrite(personVo);//
		
//		//dao를 메모리에 올린다.->서비스에 토스
//		PhonebookDao phonsbookDao= new PhonebookDao();
//
//		//dao.personInert(vo) 저장한다.->서비스에 토스
//		phonsbookDao.personInsert(personVo);
		
		//리스트로 리다이렉트
		return "redirect:/phone/list";
	}
	//등록1
	@RequestMapping(value="/phone/write" ,method={RequestMethod.GET , RequestMethod.POST } )
	//localhost:8080/phonebook5/phone/write?name=황일영&hp=010&company=02
	public String write(@RequestParam(value="name") String name,
					  @RequestParam(value="hp") String hp,
					  @RequestParam(value="company") String company )  {
		System.out.println("PhonebookController.write1()");
		System.out.println("name:"+ name +"hp:"+ hp +"company:"+ company);
		
		//vo묶는다
		PersonVo personVo = new PersonVo(name ,hp, company );
		
		//서비스를 메모리에 올리고 서비스의 메소드 사용해야함!(3단계)
		//PhonebookService phonebookService = new PhonebookService(); - 자동연결해놔서 막기
		phonebookService.exeWrite(personVo);
		
		//dao를 메모리에 올린다.
//		PhonebookDao phonsbookDao= new PhonebookDao();
//		//dao.personInert(vo) 저장한다.
//		phonsbookDao.personInsert(personVo);
		
		//리스트로 리다이렉트
		return "redirect:/phone/list";
		
	}
	//리스트
		@RequestMapping(value="/phone/list" ,method={RequestMethod.GET , RequestMethod.POST } )
		//localhost:8080/phonebook5/phone/write?name=황일영&hp=010&company=02
		private String list(Model model) {//Model에서attribute에 담아야할때만 써준다. model은 데이터고, view는 화면이다.
			System.out.println("PhonebookController.list()");
			
			//서비스를 메모리에 올리고 서비스의 메소드 사용해야함!(3단계)
			//PhonebookService phonebookService = new PhonebookService(); - 자동연결해줌.
			List<PersonVo> personList = phonebookService.exeList();
			
			//Dao사용 - >Service사용으로 주석처리
//			PhonebookDao phonebookDao = new PhonebookDao();
//			List<PersonVo> personList = phonebookDao.personSelect();
//			System.out.println(personList);
			
			model.addAttribute("pList", personList);//별명, 진짜 주소
			
//			return "/WEB-INF/views/list.jsp"; -'뷰 리졸버' 추가하고나서 빼줄거 뺴줘야 주소 오류안남
			return "list";
		}
		//삭제
		@RequestMapping(value="/phone/delete", method= {RequestMethod.GET, RequestMethod.POST})
		private String delete(@RequestParam("no") int no) { // value안쓰고 가져와도됨 설명듣기 11:09
			System.out.println("PhonebookController.delete()");
			
			//서비스
//			PhonebookService phonebookService = new PhonebookService(); - 자동연결해놔서 막기
			phonebookService.exeDelete(no);
			
			//Dao->서비스 사용으로 삭제
//			PhonebookDao phonebookDao = new PhonebookDao();
//			phonebookDao.personDelete(no);
			return "redirect:/phone/list";
		}
		//수정
		@RequestMapping(value="/phone/modify", method= {RequestMethod.GET, RequestMethod.POST})
		private String modify(@ModelAttribute PersonVo personVo) {//dispatcherservlet이 new해서 gs를 생성함 ?gs생성함확인10:49/여러가지 담아오는거라 모델로 써줌
			System.out.println("PhonebookController.modify()");
			
//			System.out.println(personVo);
			//서비스
			//PhonebookService phonebookService = new PhonebookService();- 자동연결해놔서 막기
			phonebookService.exeModify(personVo);
			
			//DB연결-> 서비스 연결로 주석처리
//			PhonebookDao phonebookDao = new PhonebookDao();
//			phonebookDao.personUpdate(personVo);
			return "redirect:/phone/list";
			
		}
		//수정폼
		@RequestMapping(value="/phone/modifyform", method= {RequestMethod.GET, RequestMethod.POST}) //=이 주소에 오면 일할 수 있게 주소좀 등록해주렴.
		private String modifyForm(@RequestParam(value="no") int no , Model model) {//파라미터 가져오기- 하나씩 꺼내오거나 두개이상은 묶어줘 의 2가지 방법이 있다. 지금은 int no 1개만 받아옴.
			System.out.println("PhonebookController.modifyForm()");
			System.out.println(no);
			
			//서비스 연결
			//PhonebookService phonebookService = new PhonebookService();- 자동연결해놔서 막기
			PersonVo personVo = phonebookService.exeModifyForm(no);
			
			//DB 연결-> 서비스 연결로 주석처리
//			PhonebookDao phonebookDao = new PhonebookDao();
//			PersonVo personVo = phonebookDao.personSelectOne(no);//한명 데이터가 PersonVo로 주소 담겨서 보내와짐
//			System.out.println(personVo);
			
			model.addAttribute("personVo" ,personVo); //이름,주소 로 담아준다.
//			return "/WEB-INF/views/modifyForm.jsp";
			return "modifyForm";
			
		}
		
}//컨트롤러 끝
