
	Chess Engine Fischerle - Notes on Distribution 


Synopsis
--------

This version of the chess engine Fischerle is in pre-1.0 state,
that is: some functionality might be not completely implemented,
and some restrictions might apply in particular environments. No
comprehensive documentation is yet available; usage of Fischerle
thus requires prior knowledge about chess engines and how to
employ them in environments that support the UCI protocol, in
particular Arena.

To be used and redistributed only in accordance with the terms
and conditions stated in the License.txt file that comes along
with this distribution.

Your feedback is highly appreciated! Please get in touch with
Fischerle's author and copyright holder

	 Dr. Roland Stuckardt
	 D-60433 Frankfurt am Main
	 Germany
	 roland@stuckardt.de
	 www.stuckardt.de 


General Technical Remarks
-------------------------

Fischerle is intended to be used via its UCI interface from within
the Arena environment. It has been merely tested under Arena;
hence, there is no guarantee that it will work properly under other
UCI-supporting environments. Its implementation of the UCI protocol
is yet partial, though it supports all levels of play ("Blitz",
"Tournament", "X Moves in Y Minutes", etc) as specified in the
"Description of the universal chess interface (UCI)" of April 2006
by Stefan Meyer-Kahlen.


Prerequisites
-------------

Fischerle is Java-based; hence, Java Runtime Environment is required.

Fischerle is intended to be used on platforms running a 64 bit version
of the Microsoft Windows 7 operating system (... though it should run
as well on other platforms, since it's implemented in Java).

Although Fischerle is intended to be employed from within the Arena
environment, it provides its own GUI that makes available some advanced
configuration options not yet offered by the as-well-provided standard
UCI engine configuration menu. Fischerle's GUI has been merely tested
on Windows platforms; hence, there is no guarantee that it will render
properly in other system environments. To enable proper display of 
chess pieces in Fischerle's own GUI,
- either font "DadaChessfield CB-Comptible" should be available
  (recommended - please watch out for DadaChessfieldCBCompatible;
  it might still be disposable for free download);
- if this font is not available, the default Unicode font
  "Arial Unicode MS" will be looked for.

That is, "DadaChessfield CB-Comptible" constitutes the so-called
preferred chess piece font. The preferred font can be altered by
specifying a different font in file config.ini (see below). This
font will then be looked for instead of "DadaChessfield CB-Comptible";
again, if not available, Fischerle will watch out for the default
Unicode font, which cannot be changed. 


Configuration File config.ini
-----------------------------

If available in the distribution's main directory, this file will
be loaded upon start-up of Fischerle. File config.ini can be edited
by the user in order to alter some standard settings of Fischerle.
If config.ini is not adapted (or possibly missing), Fischerle will
be run with the standard settings, which should work just fine.

In the current distribution Fischerle_0_9_70_SE, the following
settings might be changed by editing config.ini:

- the preferred chess piece font (see above):
  * a new font name might be specified as well as ...
  * ... the font sizes in which the pieces should be displayed
    on the small and the big chess board of Fischerle's gui;
  * moreover, the characters to be used for encoding the
    different chess pieces (and some more abstract content to
    be displayed on the small chess board) in the specified font;
- some attribute values to be inserted (i.e., information
  for the "Event" and "Site" tags etc.) when exporting
  chess games using Fischerle's "Save PGN" functionality. 

Please refer to the config.ini file that comes along with this
distribution. Its contents should be self-explaining.


Installation
------------

A) Arena:

In Arena, employ "Install New Engine ..." and select, depending upon
whether you're using a 64 bit or 32 bit system environment,
batch file Fischerle_0_9_70_SE_64.bat or Fischerle_0_9_70_SE_32.bat
available in the main directory of this distribution. (Please note
that this selection determines some crucial assumptions made regarding
the memory requirements of the hash table entries, and, thus, the
numbers of hash table entries to be made available given an amount
of HT memory in megabytes; the code base itself is identical.)

Under "Engine Management", select "UCI" as "Type".

Select the suggested Logo icon (fischerlelogo.jpg) and possibly
as well the rather peculiar country flag (hessen.gif - to be
moved to Arena's "Flags" directory) provided in the distribution's
main directory.

Navigate to the "Books" tab and select "Use Arena mainbooks with
this engine".

Note: this is the recommended setting. Fischerle provides as well
an own, automatically compiled opening book (see subdirectory "Book"),
which, however, has not yet the coverage and quality of Arena's
standard opening books.

B) ChessGUI:

In the current version of ChessGUI (0.240k), another graphical user
interface that you might wish to employ instead of Arena, starting
Fischerle with the .bat file doesn't work since, for some unknown
reason, the UCI commands don't reach the engine. In order to support
ChessGUI, .exe starters are provided that should be used instead
of the .bat starters; select the starter (Fischerle_0_9_70_SE_64.exe
or Fischerle_0_9_70_SE_32.exe) that fits your system environment
(64 or 32 bit).

Upon start-up, an empty control window will appear, indicating that
Fischerle is about to be initialized, which will take a couple
of seconds.

Please note that this ChessGUI support has only been added recently
on an ad-hoc base; thus, it has not yet been thoroughly tested. One
known issue is that Fischerle's pondering functionality doesn't seem
to harmonize with ChessGUI: in some cases, when Fischerle sends a
"bestmove <m1> ponder <m2>" command, this isn't recognized by ChessGUI,
though ChessGUI's debug window clearly shows that the command has been
properly sent and received. This looks like a subtle synchronization
or timing issue for which no quick fix seems to be available.

If you'd like to change the parameters to be submitted to the
Java Virtual Machine (e. g., the initial and maximum heap sizes,
see below) , you should first alter the .bat file and then compile
a respective new .exe starter. The name of the recommended tool,
which has as well been employed to generate the provided .exe starter,
is Bat_to_Exe_Converter, available, e. g., at
http://download.cnet.com/Bat-To-Exe-Converter/3000-2069_4-10555897.html.

Important: Please select the option "Visible Application", which means
that the empty control window will appear; otherwise, the UCI
communication between ChessGUI and engine won't work.

C) Other GUIs:

This has not yet been tested. Whenever possible, please employ the
.bat start-up mode, as the JVM parameters can be readily changed
without necessitating any .exe recompilation runs.


Usage
-----

Under Arena, start Fischerle in the usual way. Please give Fischerle
a few seconds to warm-up. During this warm-up, a dummy search is
conducted for a few seconds so that the Java JIT compiler will
generate native machine code for the time-critical parts of the code
(Java Hot Spot technology).

Visit the UCI engine configuration menu in order to experiment
with Fischerle's basic settings.

For additional options, please select option "Display Fischerle GUI"
in Fischerle's UCI engine configuration menu and keep your fingers
crossed that it will display properly in your particular system
environment.

It is strongly recommended to employ Fischerle together with Arena's
"mainbooks" as Fischerle's static evaluation criteria are of course
not designed to play openings properly.

Fischerle offers you the possibility of playing with its main static
evaluation settings by providing your own so-called evaluation factor
blocks (".efb" files) and activating them from within either the UCI
engine configuration menu or Fischerle's GUI. Take a look at the
contents of subdirectory "EFB", which should be sufficiently
self-explaining.


Hash Table Sizes 
----------------

A) On 64 Bit Systems:

Fischerle employs two different hash tables, which are implemented
based on tailor-made memory-efficient descendants of the Java class
java.util.LinkedHashMap:

* IKHT: results for internal nodes (= transposition table proper),
* ESHT: static evaluation cache.

According to diligent profiling, IKHT entries consume 56 bytes of
memory, whereas ESHT entries consume 48 bytes of memory, including
all supplementary information such as pointers and array storage
space. Assignment of 256 MB of hash thus yields 2,580,992 IKHT
and ESHT entries, assuming equal IKHT and ESHT cardinalities.

The distribution of memory between IKHT and ESHT can be altered via
option "Relative # of Elements IKHT:ESHT" available at Fischerle's
UCI configuration menu. The standard setting of 50:50 seems to yield
a good balance in practice.

Fischerle employs two small supplementary evaluation caches of fixed
sizes:

* BESHT: pawn structure evaluation cache,
* KSHT:  king safety evaluation cache.

Sizes are fixed to 100K entries (BESHT) and 350K entries (KSHT).
As the memory requirements amount to 72 bytes (BESHT) and 48 bytes
(KSHT) per entry, the fixed total memory consumption of these
structures sum up to ca. 23 MB.

B) On 32 Bit Systems:

It is important to take into acccount that ENTITY SIZES ARE
PLATFORM-SPECIFIC, that is, they are depending, in particular,
upon whether Fischerle is run on a 64 bit or a 32 bit platform.
The above entity sizes are specific to 64 bit systems. On 32 bit
systems, the entries typically use up somewhat less memory, which
implies that hash table cardinalities can be somewhat enlarged,
given the same amount of hash table space.

In order to make best use of the available HT memory given the
smaller memory footprints on 32 bit systems, Fischerle can be
employed in 32 bit mode (see batch file Fischerle_0_9_70_SE_32.bat,
additional command line parameter "32bit"). Here, taking into
account the results of diligent profiling on a 32 bit environment,
Fischerle assumes a slightly reduced memory consumption of the
HT entries:

* IKHT entries: 56 bytes	(as on 64 bit)
* ESHT entries: 40 bytes	(8 bytes less) 

Assignment of 256 MB of hash thus yields 2,796,032 IKHT and ESHT
entries (compared to merely 2,580,992 entries on 64 bit systems),
assuming equal IKHT and ESHT cardinalities.

BESHT and KSHT entries have been identified to consume only 64
and 40 bytes (8 bytes less each), respectively, giving rise to
a fixed total memory consumption of only ca. 19 MB (compared
to 23 MB above).

IMPORTANT NOTE: These assumptions regarding memory footprints
merely hold with respect to the particular Win32 / Win64 Java
implementations considered. (The 32 bit memory footprints have
been determined and verified on two different environments:
Windows 7 Home Premium 32 Bit running on 64 bit hardware, and
Windows XP 32 Bit emulated from within Windows 7 Professional
64 Bit ("XP mode").) It is quite probable that they apply as
well to, or at least constitute a good approximation of, the
characteristics of other 64 or 32 bit systems, though it might
well be the case that on genuine 32 bit hardware, the memory
requirement is yet considerably smaller. If deemed necessary,
further memory footprint models for other environments could be
added without much effort.  


Hash Tables ff: Planned Enhancements
------------------------------------

One of the next steps will probably be to differentiate
between configuration options Hash, Eval_Cache_MB (<-> ESHT),
and Pawn_Info_Cache_MB (<-> BESHT) as supported by the UCI 2
protocol. 


JVM Memory Allocation
---------------------

The preset JVM heap size values of "-Xms1400m" / "-Xmx1400m"
should be sufficient to run Fischerle with hash table sizes of
up to 1000 MB. If you intend to use Fischerle with more modest
hash sizes of up to 256 MB, you could lower heap sizes to
"-Xms700m" / "-Xmx700m", which should be more than sufficient.
In case you're using the .exe starter, you'd have to recompile
it (see above).

Please note that employing Fischerle's own opening book has a
considerable memory footprint of ca. 300 MB. Please ensure that
sufficient JVM heap space is available if you'd like to use it.

Have fun!

Roland		           Frankfurt am Main, March 25th, 2016

roland@stuckardt.de
www.stuckardt.de/index.php/schachengine-fischerle.html
