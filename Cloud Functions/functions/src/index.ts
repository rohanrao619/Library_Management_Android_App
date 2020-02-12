import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';
admin.initializeApp();


// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript

export const updateFine = functions.https.onRequest((request, response)=>{

    admin.firestore().collection('User').get().then(snap=>{
    
        snap.forEach(function(x){
    
            var temp=x.data()
           
            if((temp.type===0)&&(temp.book.length!=0))
            {
                var i,flag=0
                for(i=0;i<temp.book.length;i++)
                {
                    var z=temp.date[i].toDate()
                    var d=new Date()
                    var z1=temp.date[i].toDate()

                    z.setDate(z.getDate()+14)
                    z1.setDate(z1.getDate()+11)

                    if(z<d)
                    {
                        flag=1
                        temp.fine[i]++
                    }
                    else if(z1<d)
                    {
                        if(temp.fcmToken!=null)
                        {
                            const payload={
                                notification:
                                {
                                    title:'Return Deadline Approaching !',
                                    body:z.getDate()+'/'+(z.getMonth()+1)+'/'+z.getFullYear()+' is the deadline for Book ID : '+temp.book[i]+'\nReturn Books to avoid more Fine'
                                }
                            };
                            admin.messaging().sendToDevice(temp.fcmToken,payload).catch(err=>{
                                //
    
                            })
                        }
                    }
                }
    
                if(flag==1)
                {
                    if(temp.fcmToken!=null)
                        {

                            var tot=temp.left_fine,j
                            for(j=0;j<temp.book.length;j++)
                            {
                                tot+=temp.fine[j]

                            }

                            const payload={
                                notification:
                                {
                                    title:'Your Library Fine just Increased !',
                                    body:'Updated Fine : '+tot+'\nReturn Books to avoid more Fine'
                                }
                            };
                            admin.messaging().sendToDevice(temp.fcmToken,payload).catch(err=>{
                                // Handle Error
                            })

                        }
                        admin.firestore().doc("User/"+temp.email).set(temp).catch(error=>{
                            response.send("Error in updating")
                        })    
                }
            }       
        })
    }).catch(error1=>{
    
       response.send("Can't Read the Collection")
    })
    response.send("OK ! ")
    })
    

  
export const newBook = functions.firestore.document("Book/{Bid}").onCreate((change,context)=>{

    admin.firestore().collection("User").get().then(snap=>{

        snap.forEach(x=>{

            var z=x.data();

            if(z.fcmToken!=null&&z.type==0)
            {
                
                const payload={
                    notification:
                    {
                        title:'New Book Added to the Library !',
                        body:'Title : '+change.get("title")+'\nType : '+change.get("type")
                    }
                };

                admin.messaging().sendToDevice(z.fcmToken,payload).catch(error=>{

                    // Handle Error
                })
            }

        })

    }).catch(error=>{
        // Handle Error
    })

})