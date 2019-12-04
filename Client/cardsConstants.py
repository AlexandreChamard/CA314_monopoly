
ChancesCards = [
    'Advance to "Go"',
    'Advance to Illinois Avenue. If you pass Go, collect $200',
    'Advance to St. Charles Place. If you pass Go, collect $200',
    'Advance token to nearest Utility. It unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total 10 times the amount thrown.',
    'Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.',
    'Bank pays you dividend of $50.',
    'Get out of Jail Fee.',
    'Go Back Three spaces.',
    'Go to Jail.',
    'Make general repairs on all your property: For each house pay $25, For each hotel pay $100.',
    'Pay poor tax of $15.',
    'Take a trip to Reading Railroad.',
    'Take a walk on the Boardwalk.',
    'You have ben elected Chairman of the board. Pay each player $50.',
    'Your building and loan matures. Receive $150.',
    'You have won a crossword competition. Collect $100.'
]

CommunityCards = [
    'Advance to "Go".',
    'Bank error in your favor. Collect $200.',
    'Doctor\'s fees. Pay $50.',
    'From sale of stock you get $50.',
    'Get Out of Jail Free.',
    'Go to Jail.',
    'Grand Opera Night. Collect $50 from every player for opening night seats.',
    'Holiday Fund matures. Receive $100.',
    'Income tax refund. Collect $20.',
    'It is {It\'s} your birthday. Collect $10 from every player.',
    'Life insurance matures – Collect $100',
    'Hospital Fees. Pay $50.',
    'School fees. Pay $50.',
    'Receive $25 consultancy fee.',
    'You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.',
    'You have won second prize in a beauty contest. Collect $10.',
    'You inherit $100.'
]

Cases = [
    # name price rent 1 2 3 4 H hc
    ('Go'),
    ('Old Kent Road', 60, 2, 10, 30, 90, 160, 250, 50),
    ('Community Chest'),
    ('WhiteChapel Road', 60, 4, 20, 60, 180, 320, 450, 50),
    ('Income Tax'),
    ('Kings cross Station'),
    ('The Angel Islington', 100, 6, 30, 90, 270, 400, 550, 50),
    ('Chance'),
    ('Euston Road', 100, 6, 30, 90, 270, 400, 550, 50),
    ('Pentonville Road', 120, 8, 40, 100, 300, 450, 600, 50),
    ('Jail'),
    ('Pall Mall', 140, 10, 50, 150, 450, 625, 750, 100),
    ('Electrical Company'),
    ('Whitehall', 140, 10, 50, 150, 450, 625, 750, 100),
    ('Northumrl\'d Avenue', 160, 12, 60, 180, 500, 700, 900, 100),
    ('Marylebone Station'),
    ('Bow Street', 180, 14, 70, 200, 550, 750, 950, 100),
    ('Community Chest'),
    ('Marlborough Street', 180, 140, 70, 200, 550, 750, 950, 100),
    ('Vine Street', 200, 16, 80, 220, 600, 800, 1000, 100),
    ('Free Parking'),
    ('Strand', 220, 18, 90, 250, 700, 875, 1050, 150),
    ('Chance'),
    ('Fleet Street', 220, 18, 90, 250, 700, 875, 1050, 150),
    ('Trafalgar Square', 240, 20, 100, 300, 750, 925, 1100, 150),
    ('Fenchurch St. Station'),
    ('Leicester Square', 260, 22, 110, 330, 800, 975, 1150, 150),
    ('Coventry Street', 260, 22, 110, 330, 800, 975, 1150, 150),
    ('Water Works'),
    ('Piccadilly', 280, 24, 120, 360, 850, 1025, 1200, 150),
    ('Go To Jail'),
    ('Regent street', 300, 26, 130, 390, 900, 1100, 1275, 200),
    ('Oxford Street', 300, 26, 130, 390, 900, 1100, 1275, 200),
    ('Community Chest'),
    ('Bond Street', 320, 28, 150, 450, 1000, 1200, 1400, 200),
    ('Liverpool St. Station'),
    ('Chance'),
    ('Park Lane', 350, 35, 175, 500, 1100, 1300, 1500, 200),
    ('Super Tax'),
    ('Mayfair', 400, 50, 200, 600, 1400, 1700, 2000, 200)
]

railRoadPrice = 200
railRoadRents = [25, 50, 100, 200]

waterWorkingPrice = 150
UtilitiesRentsMult = [4, 10]