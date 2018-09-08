import begin
from bs4 import BeautifulSoup
import sqlite3
import begin
from google_images_download import google_images_download
from tkinter import *
import requests
from time import sleep


def executer_sql(conn,command):
    curs = conn.cursor ()
    return curs.execute (command)


def MAJEnregistrement(conn,ISBN,nomtable,D):
    command = "UPDATE " + nomtable +" SET "
    for i in D:
        if(i!="ISBN"):
            command = command +" \"" + str(i) + "\" = \'" + str(D[i]).replace("\'","") + "\',"

    command = command[0:-1] + " WHERE ISBN=\'"+ISBN+"\'"
    print (command)
    executer_sql(conn,command)
    command = "COMMIT;"
    executer_sql(conn,command)


def CreerEnregistrement(conn,Tuple,nomtable,valeurs):
    command = "INSERT INTO " + nomtable + "(" + Tuple + ") VALUES (\'" + valeurs + "\')"
    print(command)
    executer_sql (conn , command)
    command = "Commit;"
    executer_sql(conn,command)



def RecupererData(conn,nom):
    L = {}
    command = "SELECT * FROM " + nom + " ;"
    print(command)
    K = executer_sql(conn,command).fetchall()
    for i in K:
        L[i[6]] = list(i)
    return L

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


def Extract(string):
    pattern = '<li.*<span>(.*)</span></span> <span.*<span>(.*)</span></span> </li>'
    l = re.findall(pattern=pattern,string=str(string))
    print(l)
    return l



def Renseigner(R):
    D = {}
    URL = "https://www.fnac.com/SearchResult/ResultList.aspx?SCat=0%211&Search="+R+"&sft=1&sa=0&omnsearchtype=4"
    URL2 = "https://www.amazon.fr/s/ref=nb_sb_noss?__mk_fr_FR=%C3%85M%C3%85%C5%BD%C3%95%C3%91&url=search-alias%3Dstripbooks&field-keywords="+R+"&rh=n%3A301061%2Ck%3A"+R
    B=SoupeURL(URL2)
    L = B.get_text("")
    L3 = re.findall("(\\d{1,2} (janvier|fevrier|mars|avril|mai|juin|juillet|aout|septembre|octobre|novembre|decembre) \\d{4})",L)
    B = SoupeURL(URL)
    L = B.find_all("a",{"class":"js-minifa-title"})
    try:
        URL3 = L[0]["href"]
        print(URL3)
    except(IndexError):
        return
    B = SoupeURL (URL3)
    B = SoupeURL(URL3)
    L = B.find_all("span",{"class":"Feature-label"})
    L2 = B.find_all("span",{"class":"Feature-desc"})
    for i in L:
        D[Debaliser(i).rstrip()] = ""
    L2 = L2[::-1]
    for i in D.keys():
        D[i] = Debaliser(L2.pop()).rstrip()
    if(len(B.find_all ("h1" , {"class": "f-productHeader-Title"}))>0):
        D["Titre"] = Debaliser(B.find_all ("h1" , {"class": "f-productHeader-Title"})[0])
    if(len(B.find_all("span",{"class":"f-priceBox-price f-priceBox-price--reco checked"}))>0):
        D["Prix Fnac"] = Debaliser(B.find_all("span",{"class":"f-priceBox-price f-priceBox-price--reco checked"})[0])
    if(L3!=None and len(L3)>0):
        D["Date de parution"] = L3[0][0]
    DI = {}
    for i in D:
        if (D!="ISBN"):
            DI[i] = D[i]
    return DI

def RecupererLeResume(R,D):
    URL4 = "https://www.decitre.fr/livres/une-topologie-du-quotidien-" + R + ".html"
    try:
        D["Resume"] = "Pas de resume"
        B = SoupeURL (URL4)
        # TODO NE PAS OUBLIER DE RESET LE RESUMER DANS UNE FONCTION A PART
        if (len (B.find_all ("div" , {"class": "block-content" , "itemprop": "description"})) > 0):
            D["Resume"] = Debaliser (B.find_all ("div" , {"class": "block-content" , "itemprop": "description"})[0]).replace("'"," ")
    except(requests.exceptions.ConnectionError):
        print ("PAS DE RESUME POUR CETTE FOIS")
        D["Resume"] = "Pas de Resume"
    return D


def RecupererImage(R,D):
    response = google_images_download.googleimagesdownload()
    absolute_image_paths = response.download({"keywords":R,"limit":1,"print_urls":False})
    D["URL"] = str(absolute_image_paths.get(R)[0])
    return D



@begin.start
def Debut(ISBN,o):
    #try:
    option = int(o)
    D = {}
    if(option == 0):
        D = Renseigner(ISBN)
    if (option == 1):
        D["Resume"] = "Pas de Resume"
        D = RecupererLeResume(ISBN,D)
    if (option == 2):
        D = RecupererImage(ISBN,D)
    print("\n\n")
    for i in D:
        print("!#!" + i + ":::" + str(D[i]))
    #except(Exception):
        #print("ex")