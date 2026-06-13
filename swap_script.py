import os, re

path = r'c:\Users\admin\.gemini\antigravity\scratch\skillswapp\src\main\java\com\skillswapp'

def replacer(match):
    s = match.group(0)
    s = re.sub(r'\bTrade\b', 'Swap', s)
    s = re.sub(r'\btrade\b', 'swap', s)
    s = re.sub(r'\bTrades\b', 'Swaps', s)
    s = re.sub(r'\btrades\b', 'swaps', s)
    s = re.sub(r'\bTraded\b', 'Swapped', s)
    s = re.sub(r'\btraded\b', 'swapped', s)
    return s

for root, dirs, files in os.walk(path):
    for file in files:
        if file.endswith('.java'):
            filepath = os.path.join(root, file)
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            
            new_content = re.sub(r'"[^"]*"', replacer, content)
            
            if new_content != content:
                with open(filepath, 'w', encoding='utf-8') as f:
                    f.write(new_content)
                print('Updated ' + file)
