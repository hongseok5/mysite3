package com.bigdata2017.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bigdata2017.mysite.service.GuestbookService;
import com.bigdata2017.mysite.vo.GuestbookVo;

@Controller
@RequestMapping( "/guestbook" )
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping( "" )
	//방명록 첫 페이지 
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping( "/ajax" )
	//아쟉스 방명록 페이지 
	public String ajax() {
		return "guestbook/index-ajax";
	}
	
	@RequestMapping(
		value="/delete/{no}", 
		method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute( "no", no );
		return "guestbook/delete";
	}

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo) {
		guestbookService.deleteMessage( vo );
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(
		@ModelAttribute GuestbookVo guestbookVo) {
		//파라미터는 모델 어트리뷰트로 태그로 객체를 모델화 하고 
		guestbookService.insertMessage(guestbookVo);
		//바디에서는 객체를 파라미터로 서비스 계층의 메소드를 실행한다. 
		return "redirect:/guestbook";
	}
}
