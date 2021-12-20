import sys

LINE_LEN = 12

def bits(n):
    while True:
        yield (n & 1)
        n = n // 2

def main():
    diags = (int(line.strip(), 2) for line in sys.stdin.readlines())
    count_zeros = [0 for _ in range(LINE_LEN)]
    count_ones = [0 for _ in range(LINE_LEN)]
    for n in diags:
        bs = bits(n)
        i = 0
        for _ in range(LINE_LEN):
            b = next(bs)
            if b == 1:
                count_ones[i] += 1
            else:
                count_zeros[i] += 1
            i += 1
    gamma = 0
    epsilon = 0
    for _, zeros, ones in zip(range(LINE_LEN), count_zeros, count_ones):
        gamma <<= 1 
        epsilon <<= 1
        if ones > zeros:
            gamma |= 1
        else:
            epsilon |= 1
    
    print(gamma, epsilon, gamma * epsilon)
    print("{0:b}".format(gamma), "{0:b}".format(epsilon))



if __name__ == '__main__':
    main()