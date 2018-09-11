import pandas as pd
import sqlite3
import begin

@begin.start
def debut(BDD:"Lien du fichier de la base de donnée",FichierCSV:"Lien du fichier CSV a extraire",Table:"Nom de la table a créer"):
    c = sqlite3.connect(BDD)
    df = pd.read_csv(FichierCSV,delimiter=";",encoding="latin1")
    print(df.to_sql(Table,c))
