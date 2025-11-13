import socket

def iptourl(ip_address):
    url = socket.gethostbyaddr(ip_address)
    return url[0]
    
def urltoip(url):
    ip_address = socket.gethostbyname(url)
    return ip_address

while True:
    print("DNS Lookup Program:")
    print("1. IP to URL.")
    print("2. URL to IP.")
    print("3.Exit.")
    
    choice = input("Enter your choice:")
    
    if choice == "1":
        ip_address = input("Enter IP address:")
        url = iptourl(ip_address)
        print("URL for IP address ",url)
        
    elif choice == "2":
        url = input("Enter the URL:")
        ip_address = urltoip(url)
        print("IP address for URL ",ip_address)
        
    elif choice == "3":
        break
    
    else:
        print("Please enter correct choice.")