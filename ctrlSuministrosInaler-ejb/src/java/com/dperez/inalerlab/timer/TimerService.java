
package com.dperez.inalerlab.timer;

import com.dperez.inalerlab.email.SendMail;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.inject.Inject;

/**
 *
 * @author dperez
 */
@Singleton
@LocalBean
public class TimerService {
    
@Inject
private SendMail mail;
    
    @Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "0-6", dayOfMonth = "*", year = "*", minute = "*", second = "0")
    
    public void myTimer() {
//        mail.setupMail(host, user, password);        
//        mail.enviarMail(to, from, mensaje, asunto);
    }

    
}
