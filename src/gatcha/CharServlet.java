package gatcha;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/charServlet")
public class CharServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		CharDAO dao = new CharDAO();
		String kyarl = request.getParameter("kyarl");
		PrintWriter out = response.getWriter();
		if(kyarl!=null) {
			if(kyarl.equals("playGatcha")) {
				List<CharVO> result = new ArrayList<>();
				List<CharVO> list;
				for(int i=0; i<9; i++) {
					String draw = dao.draw();
					list = dao.listChar(draw);
					CharVO vo = new CharVO();
					vo = dao.select(list);
					result.add(vo);
				}
				
				String tenthDraw = dao.tenthDraw();
				list = dao.listChar(tenthDraw);
				CharVO vo = new CharVO();
				vo = dao.select(list);
				result.add(vo);
				
				System.out.println("========================");
				for(int i=0; i<result.size();i++) {
					System.out.println(result.get(i).getName()+"/"+result.get(i).getGrade()+"/"+result.get(i).getImgPath());
				}
				System.out.println("========================");
				
				dao.commit(result);
				request.setAttribute("result", result);
				RequestDispatcher dispatch = request.getRequestDispatcher("gatcha.jsp");
				dispatch.forward(request, response);
			}else if(kyarl.equals("result")) {
				String comporator = request.getParameter("comporator");
				String result = dao.resultJSON(comporator);
				out.print(result);
			}else if(kyarl.equals("reset")) {
				dao.reset();
				response.sendRedirect("main.jsp");
			}else if(kyarl.equals("rate")) {
				String result = dao.count();
				out.print(result);
			}else if(kyarl.equals("pickUp")) {
				
			}else if(kyarl.equals("pickupM")){
				String comporator = request.getParameter("comporator");
				String result = dao.listJSON(comporator);
				out.print(result);
			}else if(kyarl.equals("update")){
				String jsondata = request.getParameter("jsondata");
				System.out.println(jsondata);
				dao.update(jsondata);
				out.print("success");
			}else {
				out.print("<html><body><h1>잘못된 요청입니다</h1><br><a href='./main.jsp'>메인으로 돌아가기</a></body></html>");
			}
			
		}else {
			out.print("<html><body><h1>잘못된 요청입니다</h1><br><a href='./main.jsp'>메인으로 돌아가기</a></body></html>");
		}
	}
}
