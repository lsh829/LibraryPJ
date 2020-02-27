package dao;

import java.util.List;
import java.util.Scanner;

import single.LibraryStorage;
import vo.BookManageVO;
import vo.BookVO;
import vo.UserVO;

public class BookStateImpl implements BookState {
	Scanner sc = new Scanner(System.in);
	private List<BookManageVO> list = LibraryStorage.getInstance().getRentalList();

	public void BookState(BookManageVO vo) {
		list.add(vo);
	}

	@Override
	public void borrowList() {
		System.out.println("대여 도서 목록");
		for (BookManageVO vo : list) {
			if (vo.getEndDate() == null) {
				System.out.println(vo);
			}
		}

	}

	@Override
	public void returnList() {
		System.out.println("도서 반납 목록");
		for (BookManageVO vo : list) {
			if (vo.getEndDate() != null) {
				System.out.println(vo);
			}
		}

	}

	@Override
	public void findId() {
		System.out.print("아이디를 입력하세요 > ");
		String id = sc.next();
		findId(id);
	}

	@Override
	public void findId(String id) {
		try {
			boolean exist = false;
			UserVO user = LibraryStorage.getInstance().getUser(id);
			if (user == null) {
				System.out.println("아이디를 잘못 입력하셨습니다.");
				return;
			}
			System.out.println(user.getName() + "님의 대여 중인 도서 목록은 다음과 같습니다.");
			System.out.println("==========================");
			for (BookManageVO vo : list) {
				BookVO book = LibraryStorage.getInstance().getBook(vo);
				if (vo.getId().equals(id)) {
					System.out.println(vo);
					System.out.println("\t└"+book);
					exist = true;
				}
			}
			if (exist == false) {
				System.out.println(user.getName() + "님은 대여 중인 도서가 없습니다.");
			}
		} catch (Exception e) {
			System.out.println("오류가 발생했습니다. 다시 시도해 주세요.\n" + e.getMessage());
		}
	}

}
