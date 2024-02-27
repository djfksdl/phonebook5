package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.PhonebookDao;
import com.javaex.vo.PersonVo;

@Service
public class PhonebookService {

	//필드
	@Autowired
	private PhonebookDao phonebookDao;
	//생성자- 디폴트
	//메소드-gs
	//메소드-일반
	
	//등록2,1
	public int exeWrite(PersonVo personVo) {
		System.out.println("PhonebookService.exeWrite");
		
		//비지니스 로직이 있어야하는데, 여기선 없긴함. 
		//PhonebookDao phonebookDao = new PhonebookDao();- 자동연결해놔서 막기
		int count= phonebookDao.personInsert(personVo);
		
		return count ;
	}
	
	//리스트
	public List<PersonVo> exeList() {
		System.out.println("PhonebookService.exeList");
		
//		PhonebookDao phonebookDao = new PhonebookDao(); - 자동연결해놔서 막으면 됨
		List<PersonVo> personList = phonebookDao.personSelect();
		
		return personList;
	}
	
	//삭제
	public int exeDelete(int no) {
		System.out.println("PhonebookService.exeDelete");
		
		//PhonebookDao phonebookDao = new PhonebookDao();- 자동연결해놔서 막기
		int count = phonebookDao.personDelete(no);
		
		return count;//받는 이유?: 나중에 jsp2개 만들어서 지워지면 출력하는것과, 안지워졌을때 출력하는게 다를 수 있어서 쓰임! 
		
	}
	
	//수정폼
	public PersonVo exeModifyForm(int no) {
		System.out.println("PhonebookService.exeModifyForm");
		
		//PhonebookDao phonebookDao = new PhonebookDao();- 자동연결해놔서 막기
		PersonVo personVo = phonebookDao.personSelectOne(no);
		
		return personVo;
		
	}
	//수정
	public int exeModify(PersonVo personVo) {
		System.out.println("PhonebookService.exeModify");
		
		//PhonebookDao phonebookDao = new PhonebookDao();- 자동연결해놔서 막기
		int count = phonebookDao.personUpdate(personVo);
		
		return count;
		
	}
	
}
