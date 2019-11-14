/* The following code was generated by JFlex 1.7.0 tweaked for IntelliJ platform */

package com.senpure.io.support.plugin.intellij.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.senpure.io.support.plugin.intellij.psi.IoTypes.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>_IoSyntaxHighlighterLexer.flex</tt>
 */
public class _IoSyntaxHighlighterLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int MESSAGE_TAG = 2;
  public static final int FIELD_TAG = 4;
  public static final int FIELD_NAME_TAG = 6;
  public static final int BEAN_TAG = 8;
  public static final int EVENT_TAG = 10;
  public static final int ENUM_TAG = 12;
  public static final int ENUM_FIELD_TAG = 14;
  public static final int IMPORT_TAG = 16;
  public static final int NAMEPACE_TAG = 18;
  public static final int JAVA_PACKAGE_TAG = 20;
  public static final int LUA_NAMESPACE_TAG = 22;
  public static final int JS_NAMESPACE_TAG = 24;
  public static final int ENTITY_TAG = 26;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7, 
     8,  8,  9,  9, 10, 10, 11, 11, 12, 12,  0, 0
  };

  /** 
   * Translates characters to character classes
   * Chosen bits are [7, 7, 7]
   * Total runtime size is 1928 bytes
   */
  public static int ZZ_CMAP(int ch) {
    return ZZ_CMAP_A[(ZZ_CMAP_Y[ZZ_CMAP_Z[ch>>14]|((ch>>7)&0x7f)]<<7)|(ch&0x7f)];
  }

  /* The ZZ_CMAP_Z table has 68 entries */
  static final char ZZ_CMAP_Z[] = zzUnpackCMap(
    "\1\0\103\200");

  /* The ZZ_CMAP_Y table has 256 entries */
  static final char ZZ_CMAP_Y[] = zzUnpackCMap(
    "\1\0\1\1\53\2\1\3\22\2\1\4\37\2\1\3\237\2");

  /* The ZZ_CMAP_A table has 640 entries */
  static final char ZZ_CMAP_A[] = zzUnpackCMap(
    "\11\0\1\1\1\2\2\1\1\2\22\0\1\1\2\0\1\37\11\0\1\35\1\34\1\36\2\7\1\23\1\22"+
    "\1\25\1\7\1\24\3\7\1\0\1\47\1\0\1\53\3\0\2\32\1\3\12\32\1\46\1\32\1\44\2\32"+
    "\1\4\7\32\1\51\1\34\1\52\1\0\1\33\1\0\1\26\1\30\1\5\1\21\1\20\1\16\1\15\1"+
    "\32\1\10\1\42\1\45\1\13\1\40\1\11\1\14\1\41\1\32\1\31\1\6\1\12\1\27\1\43\1"+
    "\32\1\17\2\32\1\50\1\0\1\54\7\0\1\1\32\0\1\1\337\0\1\1\177\0\13\1\35\0\2\1"+
    "\5\0\1\1\57\0\1\1\40\0");

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\15\0\1\1\1\2\6\3\1\1\1\4\2\3\1\5"+
    "\1\6\1\7\1\10\1\11\4\12\1\13\1\12\1\1"+
    "\1\14\10\15\1\1\1\16\1\17\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\1\1\27\2\30\1\31"+
    "\2\32\2\33\2\34\2\35\6\3\1\36\3\3\1\37"+
    "\1\40\1\41\7\15\1\42\1\43\1\30\1\32\1\33"+
    "\1\34\1\35\11\3\1\15\1\44\5\15\3\3\1\45"+
    "\1\3\1\46\3\3\3\15\3\3\1\47\3\3\3\15"+
    "\1\50\5\3\3\15\2\3\1\51\6\3\1\52\7\3"+
    "\1\53\1\54\1\55";

  private static int [] zzUnpackAction() {
    int [] result = new int[163];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\55\0\132\0\207\0\264\0\341\0\u010e\0\u013b"+
    "\0\u0168\0\u0195\0\u01c2\0\u01ef\0\u021c\0\u0249\0\u0276\0\u02a3"+
    "\0\u02d0\0\u02fd\0\u032a\0\u0357\0\u0384\0\u03b1\0\u03de\0\u040b"+
    "\0\u0438\0\u0249\0\u0249\0\u0249\0\u0249\0\u0249\0\u0465\0\u0492"+
    "\0\u04bf\0\u04ec\0\u0519\0\u0546\0\u0573\0\u05a0\0\u05cd\0\u05fa"+
    "\0\u0627\0\u0654\0\u0681\0\u06ae\0\u06db\0\u0708\0\u0735\0\u0762"+
    "\0\u078f\0\u07bc\0\u0249\0\u07e9\0\u0816\0\u0843\0\u0870\0\u0249"+
    "\0\u089d\0\u0249\0\u08ca\0\u08f7\0\u0249\0\u0924\0\u0951\0\u097e"+
    "\0\u09ab\0\u09d8\0\u0a05\0\u0a32\0\u0a5f\0\u0a8c\0\u0ab9\0\u0ae6"+
    "\0\u0b13\0\u0b40\0\u0b6d\0\u0b9a\0\u0bc7\0\u0bf4\0\u0c21\0\u0546"+
    "\0\u0546\0\u0c4e\0\u0c7b\0\u0ca8\0\u0cd5\0\u0d02\0\u0d2f\0\u0d5c"+
    "\0\u0d89\0\u0db6\0\u0de3\0\u0e10\0\u0e3d\0\u0e6a\0\u0e97\0\u0ec4"+
    "\0\u0ef1\0\u0f1e\0\u0f4b\0\u0f78\0\u0fa5\0\u0fd2\0\u0fff\0\u102c"+
    "\0\u1059\0\u1086\0\u05cd\0\u10b3\0\u10e0\0\u110d\0\u113a\0\u1167"+
    "\0\u1194\0\u11c1\0\u11ee\0\u02a3\0\u121b\0\u02a3\0\u1248\0\u1275"+
    "\0\u12a2\0\u12cf\0\u12fc\0\u1329\0\u1356\0\u1383\0\u13b0\0\u02a3"+
    "\0\u13dd\0\u140a\0\u1437\0\u1464\0\u1491\0\u14be\0\u02a3\0\u14eb"+
    "\0\u1518\0\u1545\0\u1572\0\u159f\0\u15cc\0\u15f9\0\u1626\0\u1653"+
    "\0\u1680\0\u02a3\0\u16ad\0\u16da\0\u1707\0\u1734\0\u1761\0\u178e"+
    "\0\u02a3\0\u17bb\0\u17e8\0\u1815\0\u1842\0\u186f\0\u189c\0\u18c9"+
    "\0\u02a3\0\u02a3\0\u02a3";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[163];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\16\2\17\4\20\1\16\1\21\1\22\1\20\1\23"+
    "\4\20\1\24\1\20\4\16\2\20\1\25\2\20\3\16"+
    "\1\26\1\27\1\30\1\20\1\31\4\20\1\32\1\33"+
    "\1\34\1\35\1\16\1\36\1\16\2\17\1\37\1\40"+
    "\1\41\1\42\1\43\12\44\4\43\5\44\3\16\1\45"+
    "\1\46\7\44\1\32\1\33\1\34\1\35\1\16\1\36"+
    "\1\16\2\17\1\47\1\50\1\47\1\51\1\16\1\52"+
    "\2\47\1\53\2\47\1\54\2\47\1\55\4\16\2\47"+
    "\1\56\2\47\3\16\1\57\1\60\7\47\1\32\1\33"+
    "\1\34\1\35\1\16\1\36\1\16\2\17\4\61\1\62"+
    "\12\61\4\62\5\61\3\16\1\45\1\46\7\61\1\32"+
    "\1\33\1\34\1\35\1\63\1\36\1\16\2\17\4\64"+
    "\1\16\12\64\4\16\5\64\3\16\1\45\1\46\7\64"+
    "\1\32\1\33\1\34\1\35\1\16\1\36\1\16\2\17"+
    "\4\65\1\66\12\65\4\66\5\65\3\16\1\45\1\46"+
    "\7\65\1\32\1\33\1\34\1\35\1\16\1\36\1\16"+
    "\2\17\4\67\1\16\12\67\4\16\5\67\3\16\1\45"+
    "\1\46\7\67\1\32\1\70\1\34\1\35\1\16\1\36"+
    "\1\16\2\17\4\61\1\62\12\61\4\62\5\61\3\16"+
    "\1\71\1\46\7\61\1\72\1\33\1\34\1\35\1\63"+
    "\1\36\1\16\2\17\33\73\1\74\1\46\7\73\1\75"+
    "\1\33\1\34\1\35\1\16\1\36\1\16\2\17\32\76"+
    "\1\16\1\77\1\46\7\76\1\75\1\33\1\34\1\35"+
    "\1\16\1\36\1\16\2\17\32\100\1\16\1\101\1\46"+
    "\7\100\1\75\1\33\1\34\1\35\1\16\1\36\1\16"+
    "\2\17\32\102\1\16\1\103\1\46\7\102\1\75\1\33"+
    "\1\34\1\35\1\16\1\36\1\16\2\17\32\104\1\16"+
    "\1\105\1\46\7\104\1\75\1\33\1\34\1\35\1\16"+
    "\1\36\56\0\2\17\55\0\31\20\4\0\7\20\11\0"+
    "\31\20\4\0\1\106\6\20\11\0\23\20\1\107\5\20"+
    "\4\0\7\20\11\0\24\20\1\110\4\20\4\0\7\20"+
    "\11\0\6\20\1\111\22\20\4\0\3\20\1\112\3\20"+
    "\11\0\15\20\1\113\13\20\4\0\7\20\44\0\1\114"+
    "\16\0\2\27\1\0\52\27\3\0\15\20\1\115\13\20"+
    "\4\0\7\20\11\0\3\20\1\116\17\20\1\117\5\20"+
    "\4\0\7\20\11\0\1\44\1\120\27\44\4\0\7\44"+
    "\11\0\1\121\30\44\4\0\7\44\11\0\3\44\1\120"+
    "\25\44\4\0\7\44\11\0\2\44\1\121\26\44\4\0"+
    "\7\44\15\0\1\43\12\0\4\43\32\0\31\44\4\0"+
    "\7\44\44\0\1\122\16\0\2\46\1\0\52\46\3\0"+
    "\31\47\4\0\7\47\11\0\7\47\1\123\21\47\4\0"+
    "\7\47\11\0\5\47\1\52\1\47\1\123\1\53\20\47"+
    "\4\0\7\47\11\0\6\47\1\124\22\47\4\0\7\47"+
    "\11\0\11\47\1\125\17\47\4\0\7\47\11\0\5\47"+
    "\1\126\2\47\1\127\20\47\4\0\7\47\11\0\11\47"+
    "\1\130\17\47\4\0\7\47\11\0\11\47\1\131\17\47"+
    "\4\0\7\47\44\0\1\132\16\0\2\60\1\0\52\60"+
    "\3\0\31\61\4\0\7\61\15\0\1\62\12\0\4\62"+
    "\32\0\31\64\4\0\7\64\11\0\31\65\4\0\7\65"+
    "\15\0\1\66\12\0\4\66\32\0\31\67\4\0\7\67"+
    "\44\0\1\133\21\0\34\73\1\0\7\73\11\0\33\73"+
    "\1\134\1\0\7\73\11\0\32\76\1\0\1\76\1\0"+
    "\7\76\11\0\32\76\1\0\1\135\1\0\7\76\11\0"+
    "\32\100\1\0\1\100\1\0\7\100\11\0\32\100\1\0"+
    "\1\136\1\0\7\100\11\0\32\102\1\0\1\102\1\0"+
    "\7\102\11\0\32\102\1\0\1\137\1\0\7\102\11\0"+
    "\32\104\1\0\1\104\1\0\7\104\11\0\32\104\1\0"+
    "\1\140\1\0\7\104\11\0\31\20\4\0\1\20\1\141"+
    "\5\20\11\0\31\20\4\0\1\142\6\20\11\0\23\20"+
    "\1\143\5\20\4\0\7\20\11\0\24\20\1\144\4\20"+
    "\4\0\7\20\11\0\15\20\1\145\13\20\4\0\7\20"+
    "\11\0\23\20\1\146\5\20\4\0\7\20\6\0\2\114"+
    "\1\0\52\114\3\0\3\20\1\147\25\20\4\0\7\20"+
    "\11\0\31\20\4\0\6\20\1\150\11\0\31\20\4\0"+
    "\3\20\1\151\3\20\6\0\2\122\1\0\52\122\3\0"+
    "\26\47\1\152\2\47\4\0\7\47\11\0\7\47\1\153"+
    "\21\47\4\0\7\47\11\0\6\47\1\154\22\47\4\0"+
    "\7\47\11\0\14\47\1\155\14\47\4\0\7\47\11\0"+
    "\11\47\1\156\17\47\4\0\7\47\11\0\24\47\1\157"+
    "\4\47\4\0\7\47\11\0\11\47\1\160\17\47\4\0"+
    "\7\47\6\0\2\132\1\0\52\132\2\133\1\0\52\133"+
    "\2\122\1\0\34\134\1\122\7\134\10\122\1\0\32\135"+
    "\1\122\1\135\1\122\7\135\10\122\1\0\32\136\1\122"+
    "\1\136\1\122\7\136\10\122\1\0\32\137\1\122\1\137"+
    "\1\122\7\137\10\122\1\0\32\140\1\122\1\140\1\122"+
    "\7\140\6\122\3\0\11\20\1\161\17\20\4\0\7\20"+
    "\11\0\15\20\1\162\13\20\4\0\7\20\11\0\31\20"+
    "\4\0\6\20\1\163\11\0\31\20\4\0\1\164\6\20"+
    "\11\0\6\20\1\165\22\20\4\0\7\20\11\0\6\20"+
    "\1\166\22\20\4\0\7\20\11\0\3\20\1\167\25\20"+
    "\4\0\7\20\11\0\23\20\1\170\5\20\4\0\7\20"+
    "\11\0\23\20\1\171\5\20\4\0\7\20\11\0\5\47"+
    "\1\125\23\47\4\0\7\47\11\0\12\47\1\153\16\47"+
    "\4\0\7\47\11\0\15\47\1\172\13\47\4\0\7\47"+
    "\11\0\23\47\1\124\5\47\4\0\7\47\11\0\25\47"+
    "\1\173\3\47\4\0\7\47\11\0\10\47\1\174\20\47"+
    "\4\0\7\47\11\0\26\20\1\175\2\20\4\0\7\20"+
    "\11\0\3\20\1\176\25\20\4\0\7\20\11\0\23\20"+
    "\1\177\5\20\4\0\7\20\11\0\7\20\1\200\21\20"+
    "\4\0\7\20\11\0\23\20\1\201\5\20\4\0\7\20"+
    "\11\0\31\20\4\0\1\202\6\20\11\0\31\20\4\0"+
    "\4\20\1\203\2\20\11\0\16\47\1\204\12\47\4\0"+
    "\7\47\11\0\10\47\1\205\20\47\4\0\7\47\11\0"+
    "\15\47\1\206\13\47\4\0\7\47\11\0\7\20\1\207"+
    "\21\20\4\0\7\20\11\0\31\20\4\0\1\20\1\210"+
    "\5\20\11\0\31\20\4\0\1\211\6\20\11\0\12\20"+
    "\1\212\16\20\4\0\7\20\11\0\15\20\1\213\13\20"+
    "\4\0\7\20\11\0\23\20\1\214\5\20\4\0\7\20"+
    "\11\0\17\47\1\215\1\47\1\216\7\47\4\0\7\47"+
    "\11\0\15\47\1\153\13\47\4\0\7\47\11\0\23\47"+
    "\1\217\5\47\4\0\7\47\11\0\23\20\1\220\5\20"+
    "\4\0\7\20\11\0\15\20\1\221\13\20\4\0\7\20"+
    "\11\0\15\20\1\222\13\20\4\0\7\20\11\0\3\20"+
    "\1\223\25\20\4\0\7\20\11\0\2\20\1\224\26\20"+
    "\4\0\7\20\11\0\20\47\1\153\10\47\4\0\7\47"+
    "\11\0\22\47\1\153\6\47\4\0\7\47\11\0\6\47"+
    "\1\153\22\47\4\0\7\47\11\0\2\20\1\225\26\20"+
    "\4\0\7\20\11\0\3\20\1\226\25\20\4\0\7\20"+
    "\11\0\31\20\4\0\1\20\1\227\5\20\11\0\31\20"+
    "\4\0\5\20\1\230\1\20\11\0\15\20\1\231\13\20"+
    "\4\0\7\20\11\0\31\20\4\0\1\20\1\232\5\20"+
    "\11\0\23\20\1\233\5\20\4\0\7\20\11\0\23\20"+
    "\1\234\5\20\4\0\7\20\11\0\23\20\1\235\5\20"+
    "\4\0\7\20\11\0\2\20\1\236\26\20\4\0\7\20"+
    "\11\0\12\20\1\237\16\20\4\0\7\20\11\0\2\20"+
    "\1\240\26\20\4\0\7\20\11\0\15\20\1\241\13\20"+
    "\4\0\7\20\11\0\15\20\1\242\13\20\4\0\7\20"+
    "\11\0\15\20\1\243\13\20\4\0\7\20\6\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[6390];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\15\0\1\11\13\1\5\11\24\1\1\11\4\1\1\11"+
    "\1\1\1\11\2\1\1\11\146\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[163];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
 private int lastState;
  public _IoSyntaxHighlighterLexer() {
    this((java.io.Reader)null);
  }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public _IoSyntaxHighlighterLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    int size = 0;
    for (int i = 0, length = packed.length(); i < length; i += 2) {
      size += packed.charAt(i);
    }
    char[] map = new char[size];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < packed.length()) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + ZZ_CMAP(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { return BAD_CHARACTER;
            } 
            // fall through
          case 46: break;
          case 2: 
            { return WHITE_SPACE;
            } 
            // fall through
          case 47: break;
          case 3: 
            { return T_IDENTIFIER;
            } 
            // fall through
          case 48: break;
          case 4: 
            { yybegin(ENTITY_TAG); return T_CODE_COMMENT;
            } 
            // fall through
          case 49: break;
          case 5: 
            { yybegin(FIELD_TAG);return T_SEMICOLON;
            } 
            // fall through
          case 50: break;
          case 6: 
            { yybegin(FIELD_TAG); return T_LEFT_BRACE;
            } 
            // fall through
          case 51: break;
          case 7: 
            { return T_LEFT_BRACKET;
            } 
            // fall through
          case 52: break;
          case 8: 
            { return T_RIGHT_BRACKET;
            } 
            // fall through
          case 53: break;
          case 9: 
            { yybegin(YYINITIAL);return T_RIGHT_BRACE;
            } 
            // fall through
          case 54: break;
          case 10: 
            { return T_MESSAGE_NAME;
            } 
            // fall through
          case 55: break;
          case 11: 
            { return T_MESSAGE_ID;
            } 
            // fall through
          case 56: break;
          case 12: 
            { return T_CODE_COMMENT;
            } 
            // fall through
          case 57: break;
          case 13: 
            { yybegin(FIELD_NAME_TAG); return T_FIELD_TYPE_QUOTE;
            } 
            // fall through
          case 58: break;
          case 14: 
            { yybegin(FIELD_TAG); return T_CODE_COMMENT;
            } 
            // fall through
          case 59: break;
          case 15: 
            { return T_FIELD_NAME;
            } 
            // fall through
          case 60: break;
          case 16: 
            { return T_FIELD_INDEX;
            } 
            // fall through
          case 61: break;
          case 17: 
            { return T_EQUAL;
            } 
            // fall through
          case 62: break;
          case 18: 
            { return T_BEAN_NAME;
            } 
            // fall through
          case 63: break;
          case 19: 
            { return T_EVENT_NAME;
            } 
            // fall through
          case 64: break;
          case 20: 
            { return T_EVENT_ID;
            } 
            // fall through
          case 65: break;
          case 21: 
            { return T_ENUM_NAME;
            } 
            // fall through
          case 66: break;
          case 22: 
            { yybegin(ENUM_FIELD_TAG); return T_LEFT_BRACE;
            } 
            // fall through
          case 67: break;
          case 23: 
            { return T_SEMICOLON;
            } 
            // fall through
          case 68: break;
          case 24: 
            { return T_IMPORT_VALUE;
            } 
            // fall through
          case 69: break;
          case 25: 
            { yybegin(YYINITIAL);return T_SEMICOLON;
            } 
            // fall through
          case 70: break;
          case 26: 
            { return T_NAMESPACE_VALUE;
            } 
            // fall through
          case 71: break;
          case 27: 
            { return T_JAVA_PACKAGE_VALUE;
            } 
            // fall through
          case 72: break;
          case 28: 
            { return T_LUA_NAMESPACE_VALUE;
            } 
            // fall through
          case 73: break;
          case 29: 
            { return T_JS_NAMESPACE_VALUE;
            } 
            // fall through
          case 74: break;
          case 30: 
            { yybegin(ENTITY_TAG); return T_LINE_COMMENT;
            } 
            // fall through
          case 75: break;
          case 31: 
            { return T_MESSAGE_TYPE_CS;
            } 
            // fall through
          case 76: break;
          case 32: 
            { return T_MESSAGE_TYPE_SC;
            } 
            // fall through
          case 77: break;
          case 33: 
            { return T_LINE_COMMENT;
            } 
            // fall through
          case 78: break;
          case 34: 
            { yybegin(FIELD_TAG);return T_FIELD_COMMENT;
            } 
            // fall through
          case 79: break;
          case 35: 
            { return T_FIELD_COMMENT;
            } 
            // fall through
          case 80: break;
          case 36: 
            { yybegin(FIELD_NAME_TAG);return T_FIELD_TYPE_BASE;
            } 
            // fall through
          case 81: break;
          case 37: 
            { yybegin(ENUM_TAG); return  T_ENUM_HEAD;
            } 
            // fall through
          case 82: break;
          case 38: 
            { yybegin(BEAN_TAG); return  T_BEAN_HEAD;
            } 
            // fall through
          case 83: break;
          case 39: 
            { yybegin(EVENT_TAG); return  T_EVENT_HEAD;
            } 
            // fall through
          case 84: break;
          case 40: 
            { yybegin(IMPORT_TAG);return  T_IMPORT_HEAD;
            } 
            // fall through
          case 85: break;
          case 41: 
            { yybegin(MESSAGE_TAG); return  T_MESSAGE_HEAD;
            } 
            // fall through
          case 86: break;
          case 42: 
            { yybegin(NAMEPACE_TAG); return  T_NAMESPACE_HEAD;
            } 
            // fall through
          case 87: break;
          case 43: 
            { yybegin(JS_NAMESPACE_TAG); return  T_JS_NAMESPACE_HEAD;
            } 
            // fall through
          case 88: break;
          case 44: 
            { yybegin(JAVA_PACKAGE_TAG); return  T_JAVA_PACKAGE_HEAD;
            } 
            // fall through
          case 89: break;
          case 45: 
            { yybegin(LUA_NAMESPACE_TAG); return  T_LUA_NAMESPACE_HEAD;
            } 
            // fall through
          case 90: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
