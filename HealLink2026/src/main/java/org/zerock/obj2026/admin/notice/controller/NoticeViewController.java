package org.zerock.obj2026.admin.notice.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.obj2026.admin.notice.dto.NoticeResponse;
import org.zerock.obj2026.admin.notice.service.NoticeService;
import org.zerock.obj2026.member.dto.UserSecurityDTO;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class NoticeViewController {
    private final NoticeService noticeService;

    // 목록
    @GetMapping("/admin/notice")
    public String getAllNotice(Model model) {
        List<NoticeResponse> noticeList = noticeService.findAll();
        model.addAttribute("noticeList", noticeList);
        return "admin/notice";
    }

    // 읽기
    @GetMapping("/admin/notice/{id}")
    public String noticeDetail(@PathVariable("id") Long id, Model model) {
        NoticeResponse notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "admin/notice_detail";
    }

    // 쓰기
    @GetMapping("/admin/notice/create")
    public String createNoticePage() {
        return "admin/notice_create";
    }

    // 수정
    @GetMapping("/admin/notice/modify/{id}")
    public String editNoticePage(@PathVariable Long id, Model model,
                                 @AuthenticationPrincipal UserSecurityDTO userDetails,
                                 RedirectAttributes redirectAttributes
                                 ) {

        NoticeResponse notice = noticeService.findById(id);

        if (!notice.getWriterId().equals(userDetails.getUser().getUserId())) {
            redirectAttributes.addFlashAttribute("error", "본인의 글만 수정할 수 있습니다.");
            return "redirect:/admin/notice";
        }

        model.addAttribute("notice", notice);
        return "admin/notice_edit";
    }

}
