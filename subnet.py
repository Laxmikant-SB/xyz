# This function converts CIDR to subnet mask.
# Example: /24 → 255.255.255.0
def cidr_to_mask(cidr):
    mask = []
    bits = cidr

    # We calculate each of the 4 octets (255.x.x.x)
    for i in range(4):
        if bits >= 8:
            mask.append("255")
            bits -= 8
        elif bits > 0:
            value = 256 - (2 ** (8 - bits))   # Basic mask calculation
            mask.append(str(value))
            bits = 0
        else:
            mask.append("0")

    return ".".join(mask)


# Total IPs in the subnet
def total_ips(cidr):
    return 2 ** (32 - cidr)


# Usable hosts (cannot use 1st and last IP)
def usable_hosts(cidr):
    if cidr >= 31:   # /31 or /32 have no usable hosts
        return 0
    return (2 ** (32 - cidr)) - 2


def main():
    # FIRST NETWORK
    ip = input("IP address (e.g. 192.168.1.0): ")
    cidr = int(input("CIDR (e.g. 24): "))

    mask = cidr_to_mask(cidr)
    print(f"\nSubnet mask for {ip}/{cidr} = {mask}")
    print(f"Total IPs: {total_ips(cidr)}")
    print(f"Usable hosts: {usable_hosts(cidr)}")

    # SECOND NETWORK (NEW SUBNET)
    print("\nEnter new subnet details:")
    new_ip = input("New IP address: ")
    new_cidr = int(input("New CIDR (e.g. 26): "))

    new_mask = cidr_to_mask(new_cidr)
    print(f"\nSubnet mask for {new_ip}/{new_cidr} = {new_mask}")
    print(f"Total IPs: {total_ips(new_cidr)}")
    print(f"Usable hosts: {usable_hosts(new_cidr)}")

    # Number of subnets created
    if new_cidr > cidr:
        borrowed = new_cidr - cidr
        subnets = 2 ** borrowed
        print(f"Subnets created: {subnets}")
    else:
        print("Subnets created: N/A")


if __name__ == "__main__":
    main()
