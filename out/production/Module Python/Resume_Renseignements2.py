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
    URL4 = "https://www.decitre.fr/livres/une-topologie-du-quotidien-"+R+".html"
    try:
        B = SoupeURL(URL4)
        # TODO NE PAS OUBLIER DE RESET LE RESUMER DANS UNE FONCTION A PART
        if (len (B.find_all ("div" , {"class": "block-content" , "itemprop": "description"})) > 0):
            D["Resume"] = Debaliser (B.find_all ("div" , {"class": "block-content" , "itemprop": "description"})[0])
    except(requests.exceptions.ConnectionError):
        print("PAS DE RESUME POUR CETTE FOIS")
    DI = {}
    for i in D:
        if (D!="ISBN"):
            DI[i] = D[i]
    return DI
    #D["URL"]=absolute_image_paths.get('9788896529287')[0]


def RecupererImage(R,D):
    response = google_images_download.googleimagesdownload()
    absolute_image_paths = response.download({"keywords":R,"limit":1,"print_urls":False})
    D["URL"] = absolute_image_paths.get(R)
    return D



def add_column(nomcolonne,nom,conn):
    #nomcolonne = re.sub (" " , "" , str (nomcolonne))
    command = "ALTER TABLE " + nom + " ADD \"" + nomcolonne +"\" VARCHAR2(2500)"
    print(command)
    curs = conn.cursor()
    curs.execute(command)

def Resume_Renseignement(PointArret,NomTableSrc):
    NomTable = None
    try:
        if(not NomTableSrc.endswith("_INFO")):
            NomTable = NomTableSrc + "_INFO"
        PointArret = int (PointArret)
        Source = sqlite3.connect (r"C:\Users\Geekzone\IdeaProjects\CadocProject\BaseLivres")
        str = "PRAGMA table_info(TABLE2);"
        print (str)
        if(PointArret == 0):
            command = "CREATE TABLE " + NomTable  + " (ISBN VARCHAR2(50))"
            executer_sql (Source , command)
        print("Reprise du renseignement des lignes de la table A partir de : ",PointArret)

        DatSrc = RecupererData(Source,NomTableSrc)
        Dat = {}
        cpt = 0
        debut = 0
        str = "PRAGMA table_info("+NomTable+");"
        L = executer_sql(Source,str).fetchall()
        ListeC = []
        for i in L:
            ListeC.append(i[1])
        print("Les colonnes a éviter d'ajouter sont ")
        print(ListeC)
        for i in DatSrc:
            if(cpt<PointArret):
                cpt = cpt + 1
                continue
            Dat[i] = (Renseigner (i))

            cpt = cpt + 1
            if not Dat[i] == None:
                print ("On est au : " , cpt)
                CreerEnregistrement (Source , " ISBN " , NomTable ,i)
                for j in Dat[i]:
                    if j not in ListeC and j != "ISBN":
                        ListeC.append (j)
                        add_column (j , NomTable , Source)
                MAJEnregistrement (Source , i , NomTable , Dat[i])
        PointArret = cpt
    except(requests.exceptions.ConnectionError):
        if (NomTable == None):
            NomTable = NomTableSrc + "_INFO"
        print ("UNE EXCEPTIOON")
        sleep (15)
        Resume_Renseignement (PointArret , NomTable)

@begin.start
def Debut(PointArret,NomTableSrc):
    NomTable = None
    try:
        NomTable = NomTableSrc + "_INFO"
        PointArret = int (PointArret)
        Source = sqlite3.connect (r"C:\Users\Geekzone\IdeaProjects\CadocProject\BaseLivres")
        str = "PRAGMA table_info(TABLE2);"
        print (str)
        if(PointArret == 0):
            command = "CREATE TABLE " + NomTable  + " (ISBN VARCHAR2(50))"
            executer_sql (Source , command)
        print("Reprise du renseignement des lignes de la table A partir de : ",PointArret)

        DatSrc = RecupererData(Source,NomTableSrc)
        Dat = {}
        cpt = 0
        debut = 0
        str = "PRAGMA table_info("+NomTable+");"
        L = executer_sql(Source,str).fetchall()
        ListeC = []
        for i in L:
            ListeC.append(i[1])
        print("Les colonnes a éviter d'ajouter sont ")
        print(ListeC)
        for i in DatSrc:
            if(cpt<PointArret):
                cpt = cpt + 1
                continue
            Dat[i] = (Renseigner (i))

            cpt = cpt + 1
            if not Dat[i] == None:
                print ("On est au : " , cpt)
                CreerEnregistrement (Source , " ISBN " , NomTable ,i)
                for j in Dat[i]:
                    if j not in ListeC and j != "ISBN":
                        ListeC.append (j)
                        add_column (j , NomTable , Source)
                MAJEnregistrement (Source , i , NomTable , Dat[i])
        PointArret = cpt
    except(requests.exceptions.ConnectionError):
        if(NomTable == None):
            NomTable = NomTableSrc + "_INFO"
        print("UNE EXCEPTIOON")
        sleep(15)
        Resume_Renseignement(PointArret,NomTable)

