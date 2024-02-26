package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhonebookDao;
import com.javaex.vo.PersonVo;
@Controller
public class PhonebookController {
	//필드
	
	//생성자-디폴트 생성자 쓸거임
	
	//메소드-gs
	
	//메소드-일반
	//등록폼
	//localhost:8080/phonebook5(여기까진 공통주소)/phone/writeform
	@RequestMapping(value="/phone/writeform" ,method={RequestMethod.GET , RequestMethod.POST } )//get으로 실행시켜줘야한다. 하나만 쓰면 method= RequestMethod.GET으로 쓴다.지금은 연습하느라 두개 다 하는걸로 써놓음/ 이거 쓰는 이유는 매핑시키려고?? 2:54
	public String writeForm() {
		System.out.println("PhonebookController.writeForm()");
		return "/WEB-INF/views/writeForm.jsp"; //신한테 알려주는 역할을 함. /이건 포워드 하는 문법
	}
	//등록2
	@RequestMapping(value="/phone/write2" ,method={RequestMethod.GET , RequestMethod.POST } )
	//localhost:8080/phonebook5/phone/write?name=황일영&hp=010&company=02
	public String write(@ModelAttribute PersonVo personVo)  {
		System.out.println("PhonebookController.write()");
		//System.out.println(name + hp + company);
		
		//vo묶는다
		//PersonVo personVo = new PersonVo(name ,hp, company );
		System.out.println(personVo.toString());
		//dao를 메모리에 올린다.
		PhonebookDao phonsbookDao= new PhonebookDao();

		//dao.personInert(vo) 저장한다.
		phonsbookDao.personInsert(personVo);
		
		//리스트로 리다이렉트
		return "redirect:/phone/list";
	}
	//등록
	@RequestMapping(value="/phone/write" ,method={RequestMethod.GET , RequestMethod.POST } )
	//localhost:8080/phonebook5/phone/write?name=황일영&hp=010&company=02
	public String write(@RequestParam(value="name") String name,
					  @RequestParam(value="hp") String hp,
					  @RequestParam(value="company") String company )  {
		System.out.println("PhonebookController.write()");
		System.out.println(name + hp + company);
		
		//vo묶는다
		PersonVo personVo = new PersonVo(name ,hp, company );
		
		//dao를 메모리에 올린다.
		PhonebookDao phonsbookDao= new PhonebookDao();

		//dao.personInert(vo) 저장한다.
		phonsbookDao.personInsert(personVo);
		
		//리스트로 리다이렉트
		return "redirect:/phone/list";
		
	}
	//등록리다이렉트
		@RequestMapping(value="/phone/list" ,method={RequestMethod.GET , RequestMethod.POST } )
		//localhost:8080/phonebook5/phone/write?name=황일영&hp=010&company=02
		private String list(Model model) {//Model에서attribute에 담아야할때만 써준다. model은 데이터고, view는 화면이다.
			System.out.println("PhonebookController.list()");
			
			PhonebookDao phonebookDao = new PhonebookDao();
			List<PersonVo> personList = phonebookDao.personSelect();
//			System.out.println(personList);
			
			model.addAttribute("pList", personList);//별명, 진짜 주소
			
			return "/WEB-INF/views/list.jsp";
		}
}
