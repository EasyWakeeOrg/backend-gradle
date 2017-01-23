from requests import get
import matplotlib.pyplot as plt
from dateutil import parser
import urllib
import urllib2
import time

url = 'https://dweet.io/dweet/for/easywakeetest'
urlget ='https://dweet.io/get/dweets/for/easywakeetest'
fileTime = open("~\\EasyWakeeAlarm\\AlarmTime.txt", mode='r', buffering=1)
alarmTime = fileTime.read(5)
fileDay = open("~\\EasyWakeeAlarm\\AlarmDay.txt", mode='r', buffering=1)
alarmDay = fileDay.read(10)

#weather = get(url).json()

#day = time.strftime("%d/%m/%Y")
#hour = time.strftime("%H:%M")

#print hour

#query_args = { 'movement':'yes', 'day':day, 'time':hour }
#print query_args

#data = urllib.urlencode(query_args)
#request = urllib2.Request(url, data)  
#response = urllib2.urlopen(request).read()

#musicOn = 1

#print response

#responseJSON = get(urlget).json()
#print responseJSON

#lastDweets = [record['content'] for record in responseJSON['with']]


#if musicOn == 1:
    #i=1
    #currentDay = time.strftime("%d/%m/%Y")
    #for dweet in lastDweets:
        #if dweet.get('movement') == "yes" and dweet.get('day')==currentDay and dweet.get('time') >= alarmTime:
            #musicOn=0
            #break
        #else:
            #print i
            #i = i+1
            #print dweet.get('movement')
            #print dweet.get('day')
            #print dweet.get('time')


# musicOn      
#print responseJSON
#print lastDweets
#print alarmTime


while True:
    currentTime = time.strftime("%H:%M") +""
    if currentTime == "22:24":
        print "here"
        f = open("~\\EasyWakeeAlarm\\AlarmTime.txt", mode='r', buffering=1)
        alarmTime = f.read(5)
        fileDay = open("~\\EasyWakeeAlarm\\AlarmDay.txt", mode='r', buffering=1)
        alarmDay = fileDay.read(10)      

    #ch=int(currentTime[0:2])
    #cm=int(currentTime[3:5])
    #alarmh=int(alarmTime[0:2])
    #alarmm=int(alarmTime[3:5])

    if currentTime == alarmTime:
        print "Turn music on"
        musicOn=1
        while musicOn == 1:
            #Get the data from the movement sensor
            #Parse it to JSON

            #Dweet it
            day = time.strftime("%d/%m/%Y")#current day
            hour = time.strftime("%H:%M")#time of the data measurement
            query_args = { 'movement':'yes', 'day':'23/01/2017', 'time':hour }#Change the field value by data sent
            data = urllib.urlencode(query_args)
            request = urllib2.Request(url, data)  
            response = urllib2.urlopen(request).read()

            #READ THE LAST DWEETS
            #This reading is for loose coopling (sensor and alarmclock device on different raspberries).
            #Ideally it would be on an other raspberry but we get only one. This is also for loose coopling
            #that we need the list. Even if here the only new dweet is the one we have just posted, when
            #sensor is on a different raspberry than alarmclock then there might be several dweet between
            #the alarm clock requests (for dweets)
            responseJSON = get(urlget).json()
            lastDweets = [record['content'] for record in responseJSON['with']]         
            print "Wake up"#To replace with makin sound with the raspberry

            for dweet in lastDweets:
                if dweet.get('movement') == "yes" and dweet.get('day')==alarmDay and dweet.get('time') >= alarmTime:
                    musicOn = 0
                    #stop the sound
                    print "I've waken up"
                    break
                else:
                    print "Waiting for movement"
                    print dweet.get('movement')
                    print dweet.get('day')
                    print dweet.get('time')
        break
    else:
        print currentTime
        time.sleep(60)



print "I'm out of the loop"
