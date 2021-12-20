import sys

def main():
    depth, pos, aim = 0, 0, 0
    for line in sys.stdin.readlines():
        pieces = line.split()
        dir, x = pieces[0], int(pieces[1])

        if dir == 'forward':
            pos += x
            depth += x * aim
        elif dir == 'down':
            aim += x
        elif dir == 'up':
            aim -= x
        else:
            raise ValueError(f'Invalid direction: {dir}')
    print(pos * depth)

if __name__ == '__main__':
    main()