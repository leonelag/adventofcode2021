import sys

def main():
    depths = [int(line.strip()) for line in sys.stdin.readlines()]
    input_sz = len(depths)
    count_inc = 0

    for i in range(input_sz - 1):
        if depths[i + 1] > depths[i]:
            count_inc += 1
    print(count_inc)

if __name__ == '__main__':
    main()
