¿Es la hora de la notificación?
SI
 	Recibir señal encendido/apagado del foco *micro*
 	¿Está encendido?
 		SI
 			Establecer comunicación con el modém
 			Enviar datos al servidor
 			No enviar notificación
 		SI NO
 			Establecer conexión con el modém
 			Enviar datos al servidor
 			Enviar notificación
  			¿Responde notificación?
 				SI
 					Enviar datos al servidor
 					Recibir datos del servidor *módem*
 					Enviar datos al micro
 					Enciende el foco
 				SI NO
 					Ir a 1.
NO
 	Ir a 1.