var editorHistory = CKEDITOR.replace( 'inputHistory' );
var editorThuyTo = CKEDITOR.replace( 'inputThuyTo' );

    var editor, html = '';

    function createEditor() {
        if ( editor )
            return;

        // Create a new editor inside the <div id="editor">, setting its value to html
        var config = {};
        editor = CKEDITOR.appendTo( 'editor', config, html );
    }

    function removeEditor() {
        if ( !editor )
            return;

        // Retrieve the editor contents. In an Ajax application, this data would be
        // sent to the server or used in any other way.
        document.getElementById( 'editorcontents' ).innerHTML = html = editor.getData();
        document.getElementById( 'contents' ).style.display = '';

        // Destroy the editor.
        editor.destroy();
        editor = null;
    }

