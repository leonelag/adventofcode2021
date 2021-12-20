import sys

def main():
    depth, pos = 0, 0
    for line in sys.stdin.readlines():
        pieces = line.split()
        dir, x = pieces[0], int(pieces[1])

        if dir == 'forward':
            pos += x
        elif dir == 'down':
            depth += x
        elif dir == 'up':
            depth -= x
        else:
            raise ValueError(f'Invalid direction: {dir}')
    print(pos * depth)

if __name__ == '__main__':
    main()