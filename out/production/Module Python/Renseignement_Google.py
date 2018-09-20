from googleapiclient.discovery import build
import pprint
import json
import requests
from bs4 import BeautifulSoup
import re
import begin

def SoupeURL(URL):
    req = requests.get (URL , headers={'User-Agent': 'Mozilla/5.0'})
    p = req.content
    B = BeautifulSoup (p , "lxml")
    return B


def Debaliser(string):
    ch = str(string)
    ch = ch.replace("<br/>","")
    ch = re.sub ("<.*?>" ," ",str (ch))
    ch = re.sub("^ *","",str(ch))
    ch = re.sub("^\s*","",str(ch))
    ch = ch.rstrip()
    return ch


def Renseigner(ISBN):
    service = build('books', 'v1', developerKey="AIzaSyAJBCrIncq1Z0MCT_3fTikvXeoiMjzA0kc")
    request = service.volumes().list(q='isbn+'+ISBN)
    response = request.execute()
    print(response["totalItems"])
    D = {}
    B = None
    print(response)
    if(response["totalItems"]>0):
        for C in response["items"]:
            if("title" in C["volumeInfo"].keys()):
                D["Titre"] = C["volumeInfo"]["title"]
            if("subtitle" in C["volumeInfo"].keys()):
                D["Titre"] = D["Titre"] + " : " + C["volumeInfo"]["subtitle"]
            if("authors" in C["volumeInfo"].keys()):
                for aut in C["volumeInfo"]["authors"]:
                    D["Auteurs"] = D.get("Auteur","") + ", " +  aut
            if ("publisher" in C["volumeInfo"].keys()):
                D["Éditeur"] =  C["volumeInfo"]["publisher"]
            if("publishedDate" in C["volumeInfo"].keys()):
                D["Date de parrution"] = C["volumeInfo"]["publishedDate"]
            if("description" in C["volumeInfo"].keys()):
                D["Resume"] = C["volumeInfo"]["description"]
            if("pageCount" in C["volumeInfo"].keys()):
                D["POJO.Nombre de pages"] = C["volumeInfo"]["pageCount"]
            if("infoLink" in C["volumeInfo"].keys()):
                B = SoupeURL(C["volumeInfo"]["infoLink"])
            if("previewLink" in C["volumeInfo"].keys()):
                D["Lien Previsualistion"] = C["volumeInfo"]["previewLink"]
            break
    DD = {}
    if(B):
        L = []
        for c in B.find_all("td",{"class":"metadata_label"}):
            L.append(Debaliser(c))
        L = L[::-1]
        for c in B.find_all("td",{"class":"metadata_value"}):
            DD[L.pop()] = Debaliser(c)

    Bannir = ["Exporter la citation","ISBN","Longueur"]
    for b in DD:
        if(b not in Bannir):
            D[b] = DD[b]
    return response["totalItems"],D



@begin.start
def Debut(ISBN):
    try:
        nb,D=Renseigner(ISBN)
        if(nb==0):
            return
        for i in D:
            print("!#!" + i + ":::" + str(D[i]))
    except(Exception):
        print("")