package web.interfaz.seguridad;

import com.dperez.inalerlab.operario.Operario;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/Views/Operario/EditarOperario.xhtml")
public class FiltroAccesoAdmin implements Filter{
    
    @Override
    public void init(FilterConfig fc) throws ServletException {
        
    }
    
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sr;
        HttpServletResponse resp = (HttpServletResponse) sr1;
        Operario session = (Operario) req.getSession().getAttribute("Operario");
        int Id = 0;
        try{
            Id = Integer.parseInt(req.getParameter("id"));
        }catch(NullPointerException ex){}
        
            if((Id!=session.getIdOperario() && session.getPermisoOperario().getNombrePermiso().equals("Administrador"))
                    || Id==session.getIdOperario()){
                fc.doFilter(req, resp);
            }else{
                HttpServletResponse res = (HttpServletResponse) resp;
                res.sendRedirect(req.getContextPath() + "/Errores/Error_500.xhtml");
            }
    }
    
    @Override
    public void destroy() {
        
    }
    
}

