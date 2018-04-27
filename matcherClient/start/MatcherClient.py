from pip._vendor import requests
url = 'http://localhost:8080/'
def first_not_epmty(val, def_val = url): return val if val else def_val
url = first_not_epmty(input("Enter URL=["+url+"] or empty for default:"))

actions = {'m':{ 'func':lambda x: requests.get(x), 'act':'match'}, 
           'i':{ 'func':lambda x: requests.post(x), 'act':'insert'}}
while True :        
    mode = input("Please select mode [I]nsert, [M]athing, [E]xit :").lower()
    if mode not in('i', 'm', 'e'): continue
    elif mode == 'e': break
    else: print(actions[mode]['func'](url + actions[mode]['act'] + "/" + input("Please enter word :")).json())