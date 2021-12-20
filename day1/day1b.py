import sys

def win(arr, i):
    return arr[i] + arr[i + 1] + arr[i + 2]

def main():
    depths = [int(line.strip()) for line in sys.stdin.readlines()]
    input_sz = len(depths)
    count_inc = 0

    for i in range(input_sz - 3):
        if win(depths, i + 1) > win(depths, i):
            count_inc += 1
    print(count_inc)

if __name__ == '__main__':
    main()
